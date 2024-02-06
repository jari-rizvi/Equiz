package com.teamx.equiz.new_games

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.teamx.equiz.BR
import com.teamx.equiz.R
import com.teamx.equiz.baseclasses.BaseFragment
import com.teamx.equiz.databinding.TapTheColorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TapTheColorFragment : BaseFragment<TapTheColorBinding, TapTheColorViewModel>() {

    override val layoutId: Int
        get() = R.layout.tap_the_color
    override val viewModel: Class<TapTheColorViewModel>
        get() = TapTheColorViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    private lateinit var options: NavOptions

    private lateinit var sequence: MutableList<Int>
    private lateinit var userSequence: MutableList<Int>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        options = navOptions {
            anim {
                enter = R.anim.enter_from_left
                exit = R.anim.exit_to_left
                popEnter = R.anim.nav_default_pop_enter_anim
                popExit = R.anim.nav_default_pop_exit_anim
            }
        }

        sequence = mutableListOf()
        userSequence = mutableListOf()

        generateSequence()

        mViewDataBinding.button1.setOnClickListener {
            onButtonClick(mViewDataBinding.button1)
        }
        mViewDataBinding.button2.setOnClickListener {
            onButtonClick(mViewDataBinding.button2)
        }
        mViewDataBinding.button3.setOnClickListener {
            onButtonClick(mViewDataBinding.button3)
        }
        mViewDataBinding.button4.setOnClickListener {
            onButtonClick(mViewDataBinding.button4)
        }

    }


    private fun generateSequence() {
        sequence.clear()
        for (i in 0 until SEQUENCE_LENGTH) {
            sequence.add((1..4).random())
        }
        playSequence()
    }

    private fun playSequence() {
        for (i in 0 until sequence.size) {
            val buttonId = "button${sequence[i]}"
            val buttonResource =
                resources.getIdentifier(buttonId, "id", requireActivity().packageName)
            val button = view?.findViewById<Button>(buttonResource)
            button?.postDelayed({
                highlightButton(button)
            }, i * DELAY_BETWEEN_BUTTONS)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun highlightButton(button: Button) {
        val originalColor = button.background
        button.setBackgroundColor(R.color.gray)
        button.postDelayed({
            button.background = originalColor
        }, DELAY_BETWEEN_HIGHLIGHTS)
    }

    fun onButtonClick(view: View) {
        val button = view as Button
        val tag = button.tag.toString().toInt()
        userSequence.add(tag)
        if (userSequence.size == sequence.size) {
            checkSequence()
        }
    }

    private fun checkSequence() {
        if (userSequence == sequence) {
            Toast.makeText(
                requireActivity(),
                "Congratulations! You got it right!",
                Toast.LENGTH_SHORT
            ).show()
            userSequence.clear()
            generateSequence()
        } else {
            Toast.makeText(requireActivity(), "Oops! Try again.", Toast.LENGTH_SHORT).show()
            userSequence.clear()
        }
    }

    companion object {
        private const val SEQUENCE_LENGTH = 4
        private const val DELAY_BETWEEN_BUTTONS = 1000L
        private const val DELAY_BETWEEN_HIGHLIGHTS = 500L
    }


}