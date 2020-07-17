package com.disnovoexamen.ui.form.fragment

import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.disnovoexamen.BuildConfig
import com.disnovoexamen.R
import com.disnovoexamen.data.form.FormWebServices
import com.disnovoexamen.data.form.request.ElementRequest
import com.disnovoexamen.data.form.request.FormRequest
import com.disnovoexamen.data.form.response.ElementResponse
import com.disnovoexamen.data.form.response.FormResponse
import com.disnovoexamen.util.core.getDateService
import com.disnovoexamen.util.extension.validateEmpty
import kotlinx.android.synthetic.main.fragment_first.*
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstFragment : Fragment() {

    private val formWebService: FormWebServices by inject()
    private var id: Long = 0
    private var elementsBuilds = mutableListOf<ElementRequest>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }

    private fun addListeners() {
        jsonGet.setOnClickListener { restFormGet() }
        jsonPost.setOnClickListener { if(validateEmpty()) restFormPost() }
    }

    private fun restFormPost() {
        progressBar.visibility = View.VISIBLE
        val elements = mutableListOf<ElementRequest>()
        for (elementBuild in elementsBuilds){
            when(elementBuild.value){
                is EditText -> {
                    elements.add(ElementRequest(elementBuild.element, (elementBuild.value as EditText).text.toString()))
                }
                is CalendarView -> {
                    elements.add(ElementRequest(elementBuild.element, (elementBuild.value as CalendarView).date))
                }
                is SeekBar -> {
                    elements.add(ElementRequest(elementBuild.element, (elementBuild.value as SeekBar).progress))
                }
            }
        }

        val request = FormRequest()
        request.id = id
        request.elements = elements

        formWebService.formPost(BuildConfig.TOKEN, request)
            .enqueue(object: Callback<FormResponse> {
                override fun onFailure(call: Call<FormResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                }

                override fun onResponse(call: Call<FormResponse>, response: Response<FormResponse>) {
                    if(response.isSuccessful){
                        jsonRequest.visibility = View.VISIBLE
                        jsonRequest.text = request.getJson()
                    }else{
                        Toast.makeText(context, R.string.error_response, Toast.LENGTH_LONG).show()
                    }
                    progressBar.visibility = View.GONE
                }
            })
    }

    private fun restFormGet() {
        progressBar.visibility = View.VISIBLE
        formLayout.removeAllViews()
        elementsBuilds.clear()

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
                    }else{
                        Toast.makeText(context, R.string.error_response, Toast.LENGTH_LONG).show()
                    }
                    progressBar.visibility = View.GONE
                }
            })
    }

    private fun buildForm(form: FormResponse) {
        formLabel.visibility = View.VISIBLE
        jsonPost.isEnabled =  true

        id = form.data!!.id

        for(element in form.data!!.elements){
            buildElement(element)
        }
    }

    private fun buildElement(element: ElementResponse) {
        when(element.componentType){
            "text" ->{
                val et = EditText(context)
                et.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                et.hint = element.fieldNameView
                et.isSingleLine = true
                formLayout.addView(et)
                elementsBuilds.add(ElementRequest(element.fieldNameKey, et))
            }
            "text_multiline" ->{
                val et = EditText(context)
                et.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                et.hint = element.fieldNameView
                formLayout.addView(et)
                elementsBuilds.add(ElementRequest(element.fieldNameKey, et))
            }
            "numeric" ->{
                val et = EditText(context)
                et.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                et.hint = element.fieldNameView
                et.inputType = InputType.TYPE_CLASS_NUMBER
                et.isSingleLine = true
                formLayout.addView(et)
                elementsBuilds.add(ElementRequest(element.fieldNameKey, et))
            }
            "calendar" ->{
                val cal = CalendarView(requireContext())
                cal.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                if (element.valueElement!=null){
                    cal.date = getDateService(element.valueElement as String)!!.time
                }
                formLayout.addView(cal)
                elementsBuilds.add(ElementRequest(element.fieldNameKey, cal))
            }
            "slider" ->{
                val sb = SeekBar(context)
                val tv = TextView(context)

                sb.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                tv.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

                tv.gravity = Gravity.CENTER
                sb.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        tv.text = progress.toString()
                    }
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                })

                sb.min = element.min
                sb.max = element.max
                sb.progress = element.max
                if (element.valueElement!=null){
                    sb.progress = element.valueElement as Int
                }
                formLayout.addView(tv)
                formLayout.addView(sb)
                elementsBuilds.add(ElementRequest(element.fieldNameKey, sb))
            }
        }
    }

    private fun validateEmpty(): Boolean{
        for(elementBuild in elementsBuilds){
            when(elementBuild.value){
                is EditText ->{
                    if(!(elementBuild.value as EditText).validateEmpty(R.string.error_empty))
                        return false
                }
            }
        }
        return true
    }
}