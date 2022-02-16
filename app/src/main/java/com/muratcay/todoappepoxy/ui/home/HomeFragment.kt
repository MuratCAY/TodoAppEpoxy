package com.muratcay.todoappepoxy.ui.home

import android.os.Bundle
import android.view.View
import com.airbnb.epoxy.EpoxyTouchHelper
import com.muratcay.todoappepoxy.BaseFragment
import com.muratcay.todoappepoxy.R
import com.muratcay.todoappepoxy.database.entity.ItemEntity
import com.muratcay.todoappepoxy.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), ItemEntityInterface {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            navigateViaNavGraph(R.id.action_homeFragment_to_addItemEntityFragment)
        }

        val controller = HomeEpoxyController(this)
        binding.epoxyRecyclerView.setController(controller)

        sharedViewModel.itemEntitiesLiveData.observe(viewLifecycleOwner) { itemEntityList ->
            controller.itemEntityList = itemEntityList as ArrayList<ItemEntity>
        }

        // Setup swipe-to-delete
        EpoxyTouchHelper.initSwiping(binding.epoxyRecyclerView)
            .right()
            .withTarget(HomeEpoxyController.ItemEntityEpoxyModel::class.java)
            .andCallbacks(object :
                EpoxyTouchHelper.SwipeCallbacks<HomeEpoxyController.ItemEntityEpoxyModel>() {
                override fun onSwipeCompleted(
                    model: HomeEpoxyController.ItemEntityEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    val itemThatWasRemoved = model?.itemEntity ?: return
                    sharedViewModel.deleteItem(itemThatWasRemoved)
                }
            })
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideKeyboard(requireView())
    }

    override fun onBumpPriority(itemEntity: ItemEntity) {
        val currentPriority = itemEntity.priority
        var newPriority = currentPriority + 1
        if (newPriority > 3) {
            newPriority = 1
        }

        val updatedItemEntity = itemEntity.copy(priority = newPriority)
        sharedViewModel.updateItem(updatedItemEntity)
    }

    override fun onItemSelected(itemEntity: ItemEntity) {
        val navDirections = HomeFragmentDirections.actionHomeFragmentToAddItemEntityFragment(itemEntity.id)
        navigateViaNavGraph(navDirections)
    }
}