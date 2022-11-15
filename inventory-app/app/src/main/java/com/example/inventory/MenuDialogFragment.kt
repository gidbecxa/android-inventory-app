package com.example.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventory.data.Item
import com.example.inventory.data.getFormattedPrice
import com.example.inventory.databinding.FragmentMenuDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    MenuDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class MenuDialogFragment : BottomSheetDialogFragment() {
    //val ARG_TITLE = "Menu"

    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            (activity?.application as InventoryApplication).database.itemDao()
        )
    }

    private var _binding: FragmentMenuDialogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val navigationArgs: MenuDialogFragmentArgs by navArgs()

    lateinit var item: Item

    private fun bind(item: Item) {
        binding.apply {
            binding.itemName.text = item.itemName
            binding.itemPrice.text = item.getFormattedPrice()

            modalDelete.setOnClickListener { showConfirmationDialog() }
            modalCopy.setOnClickListener { addCopiedItem(item) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId

        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            if (selectedItem == null) {
                dismiss()
            } else {
                item = selectedItem
                bind(item)
            }
        }
    }

    private fun addCopiedItem(item: Item) {
        viewModel.addNewItem(
            item.itemName,
            item.itemPrice.toString(),
            item.quantityInStock.toString()
        )
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setTitle(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) {_, _ ->}
            .setPositiveButton(getString(R.string.yes)) {_, _ ->
                deleteItem()
            }
            .show()
    }

    private fun deleteItem() {
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    override fun dismiss() {
        super.dismiss()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}