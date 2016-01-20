package com.example.banxian911.Util;


import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by banxian911 on 2015/8/27.
 */
public abstract class BackHandledFragment extends Fragment{


    protected BackHandlerInterface backHandlerInterface;
    public abstract String getTagText();
    public abstract boolean onBackPressed();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof BackHandlerInterface)){
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface ");
        }else {
            backHandlerInterface = (BackHandlerInterface)getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        backHandlerInterface.setSelectedFragment(this);
    }
    public interface BackHandlerInterface{
        public void setSelectedFragment (BackHandledFragment backHandledFragment);
    }

}
