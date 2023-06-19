package com.cdp.ecodoctapp.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cdp.ecodoctapp.service.UserService;

public class GalleryViewModel extends ViewModel {

    UserService userService = new UserService();
    private final MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(userService.getLoggedUser());


    }

    public LiveData<String> getText() {
        return mText;
    }
}