package com.disnovoexamen.ui.form.fragment

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.disnovoexamen.BuildConfig
import com.disnovoexamen.R
import com.disnovoexamen.data.form.FormWebServices
import com.disnovoexamen.data.form.response.FormResponse
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirstFragment : Fragment() {

    private val formWebService: FormWebServices by inject()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jsonRequest.movementMethod = ScrollingMovementMethod()
        jsonResponse.movementMethod = ScrollingMovementMethod()
        addListeners()
    }

    private fun addListeners() {
        jsonGet.setOnClickListener { restFormGet() }
    }

    private fun restFormGet() {
        progressBar.visibility = View.VISIBLE

        formWebService.formGet(BuildConfig.TOKEN)
            .enqueue(object: Callback<FormResponse> {
                override fun onFailure(call: Call<FormResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }

                override fun onResponse(call: Call<FormResponse>, response: Response<FormResponse>) {
                    if(response.isSuccessful){
                        jsonResponse.text = response.body()!!.data!!.getJson()
                        buildForm(response.body()!!)
                        formLabel.visibility = View.VISIBLE
                    }else{
                        Toast.makeText(context, R.string.error_response, Toast.LENGTH_LONG).show()
                    }
                    progressBar.visibility = View.GONE
                }
            })
    }

    private fun buildForm(form: FormResponse) {

    }
}