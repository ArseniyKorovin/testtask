package ru.homedevelopment.testtask.presentation.authorization

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.homedevelopment.testtask.databinding.FragmentAuthorizationBinding
import ru.homedevelopment.testtask.R
import ru.homedevelopment.testtask.utils.CORRECT_CODE
import java.text.SimpleDateFormat
import java.util.*

class AuthorizationFragment : Fragment() {

    lateinit var binding: FragmentAuthorizationBinding
    lateinit var timer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater)
        return binding.root
    }

    private val viewModel: AuthorizationViewModel by viewModels()
    private var errorDialog: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setListeners()
        observeErrors()
        observeCode()
        checkAuthorisation()
    }

    private fun checkAuthorisation() {
        if (viewModel.isAuthorized()) {
            timer = object : CountDownTimer(
                2000,
                2000
            ) {
                override fun onTick(millisUntilFinished: Long) {}

                override fun onFinish() {
                    binding.apply {
                        etPhone.setText("")
                        etCode.setText("")
                        etCode.visibility = View.GONE
                    }
                    viewModel.clearCodeAndNumber()
                    findNavController().navigate(R.id.navigation_main)
                }
            }
            timer.start()
        }
    }

    private fun observeCode() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.code.collect { code ->
                if (code == null) {
                    return@collect
                }
                binding.etCode.visibility = View.VISIBLE
                binding.tvRequestCodeInfo.visibility = View.VISIBLE
                binding.btnRequestCode.isClickable = false
                timer = object : CountDownTimer(60000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val sdf = SimpleDateFormat("ss", Locale.getDefault())
                        binding.tvRequestCodeInfo.text = activity?.getString(
                            R.string.title_request_code,
                            sdf.format(millisUntilFinished)
                        )
                    }

                    override fun onFinish() {
                        binding.btnRequestCode.isClickable = true
                    }
                }
                timer.start()
            }
        }
    }

    private fun observeErrors() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.error.collect { wrapper ->
                wrapper.getContentIfNotHandled()?.let { errorMessage ->
                    showErrorAlertDialog(errorMessage)
                }
            }
        }
    }

    private fun showErrorAlertDialog(message: String) {
        if (errorDialog?.isShowing == true) return

        if (errorDialog == null) {
            errorDialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
                .setTitle(R.string.title_error)
                .setMessage(message)
                .setPositiveButton(R.string.btn_ok) { _, _ -> }
                .show()

            return
        }

        errorDialog!!.setMessage(message)
        errorDialog!!.show()
    }

    private fun setListeners() {
        binding.apply {
            etPhone.addTextChangedListener(
                object : TextWatcher {
                    private var mSelfChange = false

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                        if (s == null || mSelfChange) {
                            return
                        }

                        val preparedStr = s.replace(Regex("(\\D*)"), "")
                        var resultStr = StringBuilder()
                        for (i in preparedStr.indices) {
                            resultStr = when (i) {
                                0 -> resultStr.append("+7")
                                1 -> resultStr.append(preparedStr[i])
                                2 -> resultStr.append(preparedStr[i])
                                3 -> resultStr.append(preparedStr[i])
                                4 -> resultStr.append(preparedStr[i])
                                5 -> resultStr.append(preparedStr[i])
                                6 -> resultStr.append(preparedStr[i])
                                7 -> resultStr.append(preparedStr[i])
                                8 -> resultStr.append(preparedStr[i])
                                9 -> resultStr.append(preparedStr[i])
                                10 -> resultStr.append(preparedStr[i])
                                else -> resultStr
                            }
                        }
                        mSelfChange = true

                        val oldSelectionPos = binding.etPhone.selectionStart
                        val isEdit = binding.etPhone.selectionStart != binding.etPhone.length()

                        binding.etPhone.setText(resultStr)
                        if (isEdit) {
                            binding.etPhone.setSelection(
                                if (oldSelectionPos > resultStr.length) resultStr.length
                                else oldSelectionPos
                            )
                        } else {
                            binding.etPhone.setSelection(resultStr.length)
                        }
                        mSelfChange = false
                    }

                    override fun afterTextChanged(s: Editable?) {
                        viewModel.setPhone(s.toString().drop(3))
                    }
                }
            )

            btnRequestCode.setOnClickListener {
                viewModel.setPhone(etPhone.text.toString())
                viewModel.getCode(getString(R.string.phone_number_not_correct))
            }

            etCode.addTextChangedListener(
                object : TextWatcher {
                    private var mSelfChange = false

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                        if (s == null || mSelfChange) {
                            return
                        }

                        val preparedStr = s.replace(Regex("(\\D*)"), "")
                        var resultStr = StringBuilder()
                        for (i in preparedStr.indices) {
                            resultStr = when (i) {
                                0 -> resultStr.append(preparedStr[i])
                                1 -> resultStr.append(preparedStr[i])
                                2 -> resultStr.append(preparedStr[i])
                                3 -> resultStr.append(preparedStr[i])
                                else -> resultStr
                            }
                        }
                        mSelfChange = true

                        val oldSelectionPos = binding.etCode.selectionStart
                        val isEdit = binding.etCode.selectionStart != binding.etPhone.length()

                        binding.etCode.setText(resultStr)
                        if (isEdit) {
                            binding.etCode.setSelection(
                                if (oldSelectionPos > resultStr.length) resultStr.length
                                else oldSelectionPos
                            )
                        } else {
                            binding.etCode.setSelection(resultStr.length)
                        }
                        mSelfChange = false
                    }

                    override fun afterTextChanged(s: Editable?) {
                        val code = s.toString()
                        if (code.length == 4) {
                            val codeIsCorrect = viewModel.checkCode(
                                code.toInt(),
                                getString(R.string.code_not_correct)
                            )
                            if (codeIsCorrect) {
                                viewModel.authorize()
                                timer = object : CountDownTimer(
                                    2000,
                                    2000
                                ) {
                                    override fun onTick(millisUntilFinished: Long) {}

                                    override fun onFinish() {
                                        binding.apply {
                                            etPhone.setText("")
                                            etCode.setText("")
                                            etCode.visibility = View.GONE
                                        }
                                        viewModel.clearCodeAndNumber()
                                        findNavController().navigate(R.id.navigation_main)
                                    }
                                }
                                timer.start()
                            }
                        }
                    }
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }
}