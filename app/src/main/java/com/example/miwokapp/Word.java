package com.example.miwokapp;

public class Word {

    private String mDefaultTranslation;

    private String mMiwokTranslation;

    private int mImageResourseId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    private int mAudioResourseId;



    public Word(String defaultTranslation, String miwokTranslation, int AudioResourseId)
    {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourseId = AudioResourseId;
    }

    public Word(String defaultTranslation, String miwokTranslation, int ImageResourseId, int AudioResourseId)
    {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourseId =  ImageResourseId;
        mAudioResourseId = AudioResourseId;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImageResourseId() { return mImageResourseId; }

    public boolean hasImage(){
        return mImageResourseId != NO_IMAGE_PROVIDED;
    }

    public int getmAudioResourseId() { return mAudioResourseId; }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourseId=" + mImageResourseId +
                ", mAudioResourseId=" + mAudioResourseId +
                '}';
    }
}
