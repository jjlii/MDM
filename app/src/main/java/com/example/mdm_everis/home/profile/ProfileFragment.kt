package com.example.mdm_everis.home.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.domain.user.UserResponse
import com.example.mdm_everis.MainActivity
import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.example.mdm_everis.home.reserves.ReservesFragmentArgs
import com.example.mdm_everis.home.reserves.ReservesViewModel
import kotlinx.android.synthetic.main.profile_data_card_view.*


class ProfileFragment : BaseFragment<ProfileViewModel>() {

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.profile_fragment
    override fun getViewModel() = ProfileViewModel::class

    //******************************************* End BaseFragment abstract ************************

    var userData : UserResponse? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseNavBar.menu.getItem(3).isChecked = true
        userData = (activity as MainActivity).getUser()
        tv_name_content.text = userData?.name
        tv_email_content.text = userData?.email
    }

    //******************************************* Init *********************************************
    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************
    //******************************************* End Observers ************************************

}
