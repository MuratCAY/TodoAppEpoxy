package com.muratcay.todoappepoxy.ui.profile

import android.os.Bundle
import android.view.View
import com.muratcay.todoappepoxy.BaseFragment
import com.muratcay.todoappepoxy.database.entity.CategoryEntity
import com.muratcay.todoappepoxy.databinding.FragmentAddCategoryBinding
import java.util.*

class AddCategoryFragment : BaseFragment<FragmentAddCategoryBinding>(FragmentAddCategoryBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryNameEditText.requestFocus()
        mainActivity.showKeyboard(requireView())
        binding.saveButton.setOnClickListener {
            saveCategoryToDatabase()
        }

        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner) { completed ->
            if (completed) {
                navigateUp()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        sharedViewModel.transactionCompleteLiveData.postValue(false)
    }

    private fun saveCategoryToDatabase() {
        val categoryName = binding.categoryNameEditText.text.toString().trim()
        if (categoryName.isEmpty()) {
            binding.categoryNameTextField.error = "* Required field"
            return
        }

        val categoryEntity = CategoryEntity(
            id = UUID.randomUUID().toString(),
            name = categoryName
        )

        sharedViewModel.insertCategory(categoryEntity)
    }
}
