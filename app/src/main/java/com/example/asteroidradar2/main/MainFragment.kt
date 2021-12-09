package com.example.asteroidradar2.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asteroidradar2.databinding.FragmentMainBinding
import com.example.asteroidradar2.domain.Asteroid

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val asteroidListAdapter= AsteroidAdapter(AsteroidAdapter.OnClickListener{
        viewModel.displayPropertyDetails((it))
    })


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.asteroids.observe(viewLifecycleOwner, Observer <List<Asteroid>> { asteroids ->
            asteroids?.apply {
                asteroidListAdapter.submitList(this)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.asteroidRecycler.adapter=asteroidListAdapter
        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                this.findNavController().navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return binding.root
    }


}

