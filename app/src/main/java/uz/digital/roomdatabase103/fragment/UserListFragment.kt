package uz.digital.roomdatabase103.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.digital.roomdatabase103.MainActivity
import uz.digital.roomdatabase103.R
import uz.digital.roomdatabase103.adapter.UserAdapter
import uz.digital.roomdatabase103.database.UserDatabase
import uz.digital.roomdatabase103.databinding.FragmentUserListBinding


class UserListFragment : Fragment(R.layout.fragment_user_list) {
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private val userAdapter by lazy { UserAdapter() }
    private val userDatabase by lazy { UserDatabase(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserListBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        binding.rv.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_addUpdateFragment)
        }
        userAdapter.setList(userDatabase.dao.getAllUsers().toMutableList())
        userAdapter.onClick = {
            val user = bundleOf("user" to it)
            findNavController().navigate(R.id.action_userListFragment_to_detailFragment, user)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}