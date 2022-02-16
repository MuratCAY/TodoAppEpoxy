import android.os.Bundle
import android.view.View
import com.muratcay.todoappepoxy.BaseFragment
import com.muratcay.todoappepoxy.R
import com.muratcay.todoappepoxy.databinding.FragmentProfileBinding
import com.muratcay.todoappepoxy.ui.profile.ProfileEpoxyController


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val profileEpoxyController = ProfileEpoxyController(
        onCategoryEmptyStateClicked = ::onCategoryEmptyStateClicked
    )

    override fun onResume() {
        super.onResume()
        mainActivity.hideKeyboard(requireView())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.epoxyRecyclerView.setController(profileEpoxyController)

        sharedViewModel.categoryEntitiesLiveData.observe(viewLifecycleOwner) { categoryEntityList ->
            profileEpoxyController.categories = categoryEntityList
        }
    }

    private fun onCategoryEmptyStateClicked() {
        navigateViaNavGraph(R.id.action_profileFragment_to_addCategoryEntityFragment)
    }
}