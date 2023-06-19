package com.cdp.ecodoctapp.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cdp.ecodoctapp.databinding.FragmentGalleryBinding;
import com.cdp.ecodoctapp.repository.UserRepository;
import com.cdp.ecodoctapp.service.UserService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private UserService userService;

    private UserRepository userRepository = new UserRepository();

    private DatabaseReference usuariosReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        usuariosReference = FirebaseDatabase.getInstance().getReference("UserEntity");


        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        userService = new UserService();
        userService.getLoggedUser();
        try {
            userService.getUsuario();
            Thread.sleep(9000);
            Log.d("USER NUEVITO" , userService.getUser().toString());
        } catch (InterruptedException e) {
            Toast.makeText(getContext(), "Error al traer usuario", Toast.LENGTH_SHORT).show();

        }
        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}