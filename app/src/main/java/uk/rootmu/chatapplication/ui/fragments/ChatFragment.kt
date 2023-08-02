package uk.rootmu.chatapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import uk.rootmu.chatapplication.data.local.model.User
import uk.rootmu.chatapplication.databinding.FragmentChatBinding
import uk.rootmu.chatapplication.ui.RecipientListener
import uk.rootmu.chatapplication.ui.adapters.MessageAdapter
import uk.rootmu.chatapplication.ui.viewmodels.ChatViewModel
import uk.rootmu.chatapplication.utils.collectFlow

/**
 * A chat [Fragment] subclass that displays a list of messages and allows more to entered.
 */
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MessageAdapter
    private val viewModel: ChatViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        _binding = FragmentChatBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@ChatFragment
            vm = viewModel
            with(messageRecyclerView) {
                layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                adapter = MessageAdapter().apply {
                    this@ChatFragment.adapter = this

                    registerAdapterDataObserver(object :
                        RecyclerView.AdapterDataObserver() {
                        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                            this@with.scrollToPosition(positionStart)
                        }
                    })
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        collectFlow(viewModel.allMessages, adapter::submitList)
        binding.messageEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                viewModel.sendMessage()
                true
            } else false
        }
    }

    override fun onResume() {
        super.onResume()
        with(requireActivity()) {
            if (this is RecipientListener) {
                onRecipientChanged(User.RECIPIENT)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}