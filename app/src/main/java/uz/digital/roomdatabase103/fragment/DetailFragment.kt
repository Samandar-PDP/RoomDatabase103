package uz.digital.roomdatabase103.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import uz.digital.roomdatabase103.MainActivity
import uz.digital.roomdatabase103.R
import uz.digital.roomdatabase103.database.UserDatabase
import uz.digital.roomdatabase103.database.UserEntity
import uz.digital.roomdatabase103.databinding.FragmentDetailBinding
import uz.digital.roomdatabase103.databinding.FragmentUserListBinding
import uz.digital.roomdatabase103.uti.snackBar

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: UserEntity
    private val database by lazy { UserDatabase(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getParcelable("user")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            toolBar.title = user.name
            textData.text = "${user.name}\n${user.lastName}\n${user.age}"
        }
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnDelete.setOnClickListener {
            showDialog()
        }
        binding.btnUpdate.setOnClickListener {
            val user = bundleOf("user" to user)
            findNavController().navigate(R.id.action_detailFragment_to_addUpdateFragment, user)
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete")
            setMessage("Do you want to delete this user?")
            setPositiveButton("Yes") { di, _ ->
                database.dao.deleteUser(user)
                snackBar("Deleted!")
                findNavController().popBackStack()
            }
            setNegativeButton("No", null)
        }.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}