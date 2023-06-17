package com.cdp.ecodoctapp.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdp.ecodoctapp.MainActivity;
import com.cdp.ecodoctapp.R;
import com.cdp.ecodoctapp.databinding.FragmentSlideshowBinding;
import com.cdp.ecodoctapp.entity.Message;
import com.cdp.ecodoctapp.service.UserService;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private UserService userService = new UserService();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        logout(root);
        return root;
    }
    public void logout (View view){

        Message message =  userService.logout();
        Toast.makeText(getContext(), message.getMessage(), Toast.LENGTH_SHORT).show();
        onDestroyView();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}