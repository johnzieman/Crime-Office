package com.ziemapp.johnzieman.crimeoffice

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ziemapp.johnzieman.crimeoffice.databinding.FragmentCrimeListBinding
import com.ziemapp.johnzieman.crimeoffice.models.Crime

class CrimeListFragment : Fragment() {
    private val crimeListViewModel by lazy {
        ViewModelProvider(this).get(CrimeListViewModel::class.java)
    }

    private var _binding: FragmentCrimeListBinding? = null
    private val binding get() = _binding!!

    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
                viewLifecycleOwner,
                Observer { crimes ->
                    updateUI(crimes)
                }
        )
        if(crimeListViewModel.crimeListLiveData.value != null) {
            binding.promptVisibleTextview.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.new_crime -> {
                addCrimes()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    private fun addCrimes(){
        val crime = Crime()
        crimeListViewModel.addCrime(crime)
        callbacks?.onCrimeSelected(crime.id)
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onStart() {
        super.onStart()
        binding.imageButton.setOnClickListener {
            addCrimes()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateUI(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes)
        binding.recyclerView.adapter = adapter
    }

    // setting adapter with viewHolder
    private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<CrimeViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeViewHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeViewHolder(view)
        }

        override fun onBindViewHolder(holder: CrimeViewHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

        override fun getItemCount() = crimes.size
    }

    private inner class CrimeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var crime: Crime
        val titleTextView = view.findViewById(R.id.crime_title) as TextView
        val dateTextView = view.findViewById(R.id.crime_date) as TextView
        val crimeSolvedImageView = view.findViewById(R.id.crime_solved) as ImageView

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = crime.title
            dateTextView.text = crime.date.toString()
            crimeSolvedImageView.visibility = if(crime.isSolved) View.VISIBLE else View.INVISIBLE
        }

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            callbacks?.onCrimeSelected(crime.id)
        }

    }
}