package ru.homedevelopment.testtask.presentation.image

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.homedevelopment.testtask.R
import ru.homedevelopment.testtask.databinding.FragmentImageBinding

class ImageFragment: Fragment() {

    companion object {
        private const val KEY_IMAGE_URL = "IMAGE_URL"

        fun createBundle(imageUrl: String): Bundle {
            val bundle = Bundle()
            bundle.putString(KEY_IMAGE_URL, imageUrl)
            return bundle
        }
    }

    lateinit var binding: FragmentImageBinding
    private lateinit var viewModel: ImageViewModel
    private var warningDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentImageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        initViewModel()
        renderImage()
        initMenu()
    }

    private fun initMenu() {
        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(createMenuProvider(), viewLifecycleOwner, Lifecycle.State.CREATED)
    }

    private fun createMenuProvider(): MenuProvider {
        return object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.image_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.btn_delete) {
                    showWarningAlert(getString(R.string.info_delete_image))
                }
                return false
            }
        }
    }

    private fun showWarningAlert(message: String) {
        if (warningDialog?.isShowing == true) return

        if (warningDialog == null) {
            warningDialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
                .setTitle(R.string.title_warning)
                .setMessage(message)
                .setPositiveButton(R.string.btn_delete) { _, _ ->
                    viewModel.deleteImageUrl()
                    requireActivity().onBackPressed()
                }
                .setNegativeButton(R.string.btn_cancel) { _, _ ->}
                .show()
        }
    }

    private fun renderImage() {
        binding.image.load(viewModel.imageUrl)
    }

    private fun initViewModel() {
        val imageUrl = arguments?.getString(KEY_IMAGE_URL) ?: return
        val factory = ImageViewModel.Factory(imageUrl)

        viewModel = ViewModelProvider(
            this@ImageFragment,
            factory
        ) [ImageViewModel::class.java]
    }
}