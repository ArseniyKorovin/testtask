package ru.homedevelopment.testtask.presentation.main

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.homedevelopment.testtask.R
import ru.homedevelopment.testtask.databinding.FragmentMainBinding
import ru.homedevelopment.testtask.presentation.image.ImageFragment

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getImageUrls()
        observeImagesUrls()
        initMenu()
        findNavController().addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.navigation_authorization) {
                viewModel.unAuthorization()
            }
        }
    }

    private fun getImageUrls() {
        viewModel.getImages()
    }

    private fun initMenu() {
        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(createMenuProvider(), viewLifecycleOwner, Lifecycle.State.CREATED)
    }

    private fun createMenuProvider(): MenuProvider {
        return object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == android.R.id.home) {
                    viewModel.unAuthorization()
                    requireActivity().onBackPressed()
                }
                return false
            }
        }
    }

    private fun observeImagesUrls() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.imagesUrls.collect { images ->
                if (images.isEmpty()) renderLoadingImages()
                else renderImages()
            }
        }
    }

    private fun renderLoadingImages() {
        binding.apply {
            rvImages.visibility = View.GONE
            pbMain.visibility = View.VISIBLE
        }
    }

    private fun renderImages() {
        binding.apply {
            pbMain.visibility = View.GONE
            rvImages.visibility = View.VISIBLE
            rvImages.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = MainAdapter(images = viewModel.imagesUrls.value) { navigateToImage(it) }
            }
        }
    }

    private fun navigateToImage(imageUrl: String) {
        findNavController().navigate(R.id.navigation_image, ImageFragment.createBundle(imageUrl))
    }
}