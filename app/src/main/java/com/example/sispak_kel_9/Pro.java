package com.example.sispak_kel_9;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Pro extends Fragment {
    private View view;
    private VideoView videoView;

    public Pro() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pro, container, false);

        // Inisialisasi VideoView
        videoView = view.findViewById(R.id.videoView);

        // Set sumber video (ganti dengan sumber video yang sesuai)
        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.record3;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // Inisialisasi MediaController
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Mulai pemutaran video
        videoView.start();

        return view;
    }
}
