package com.android.sunghyun.zigbang_project;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.sunghyun.zigbang_project.domain.Room;

import java.io.IOException;

/**
 * Created by YKC on 2016. 11. 8..
 */
public class PostFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = "PostFragment";
    private final static int REQ_CODE_CAMERA = 10;
    private final static int REQ_CODE_GALLERY = 20;
    private final static int REQ_CODE_ADDRESS = 30;

    private OnFragmentInteractionListener mListener;

    FloatingActionButton btnPost; // 등록 버튼
    Button btnGallery,btnTake,btnAddress;

    EditText roomTitle;    // 방제목
    EditText price; // 매매가
    EditText deposite;// 보증금
    EditText rent; // 월세
    ImageView roomPhoto1; // 사진 1개 필수
    EditText roomCount;// 방개수
    EditText roomSize;// 평수

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        // 등록 버튼
        btnPost = (FloatingActionButton) view.findViewById(R.id.fab);
        btnGallery = (Button) view.findViewById(R.id.btnGallery);
        btnTake = (Button) view.findViewById(R.id.btnTake);
        btnAddress = (Button) view.findViewById(R.id.btnAddress);

        btnPost.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnTake.setOnClickListener(this);
        btnAddress.setOnClickListener(this);

        roomTitle = (EditText) view.findViewById(R.id.etTitle);
        price = (EditText) view.findViewById(R.id.price);
        deposite = (EditText) view.findViewById(R.id.etDeposite);
        rent = (EditText) view.findViewById(R.id.etMonthlyRent);
        // 사진 1개 필수
        roomPhoto1 = (ImageView) view.findViewById(R.id.roomPhoto1);
        roomCount = (EditText) view.findViewById(R.id.roomCount);// 방개수
        roomSize = (EditText) view.findViewById(R.id.roomSize);// 평수

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.fab:
                if(validate()){
                    // firebase database에 등록
                }
                break;
            case R.id.btnGallery:
                intent = new Intent(Intent.ACTION_PICK
                        , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*"); // 이미지만 필터링
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),REQ_CODE_GALLERY);
                break;
            case R.id.btnTake:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQ_CODE_CAMERA);
                break;
            case R.id.btnAddress:
                intent = new Intent(getContext(),PlaceSearchActivity.class);
                startActivityForResult(intent, REQ_CODE_ADDRESS);
                break;
        }
    }

    public void post(){
        Room room = new Room();
    }

    public boolean validate(){
        boolean flag = false;
        // 여기서 모든 필드의 값이 정상적으로 들어갔는지 체크
        Log.d(TAG,"roomPhoto="+roomPhoto1.getDrawable());
        if(roomPhoto1.getDrawable() == null){ // 이미지 정상여부

        }
        // 방제목
        if(roomTitle.getText().toString().length() > 1) {

        }
        // 주소체크
//        if(){
//
//        }

        return flag;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (null != data.getData()) {
                Bitmap image = null;
                Uri uri = data.getData();
                switch (requestCode) {
                    case REQ_CODE_GALLERY:
                        //roomPhoto1.setImageURI(uri);
                        //break;
                    case REQ_CODE_CAMERA:
                        String imgPath = getRealPathFromURI(uri);
                        Log.i("image","imgPath="+imgPath);
                        image = getThumbnailImage(imgPath);
                        roomPhoto1.setImageBitmap(image);
                        //imageView.setImageURI(uri);
                        break;
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private Bitmap getThumbnailImage(String imgPath) {
        Bitmap image = null;
        try {
            // 이미지 축소
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 3; // 1/3로 축소

            image = BitmapFactory.decodeFile(imgPath,options);

            Log.i("image","instance="+image);
            int exifDegree = exifOrientationToDegrees(imgPath);
            Log.i("image","exifDegree="+exifDegree);
            image = rotateImage(image, exifDegree);

        }catch (Exception e){
            Log.e("Thumbnail Error",e.toString());
            e.printStackTrace();
        }
        return image;
    }


    /**
     * EXIF정보를 회전각도로 변환하는 메서드
     * @param imgPath 이미지 경로
     * @return 실제 각도
     */
    public int exifOrientationToDegrees(String imgPath){
        int degree = 0;
        ExifInterface exif = null;

        try{
            exif = new ExifInterface(imgPath);
        }catch (IOException e){
            Log.e("exifOrientation", "cannot read exif");
            e.printStackTrace();
        }

        if (exif != null){
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1){
                // We only recognize a subset of orientation tag values.
                switch(orientation){
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    // 이미지 회전 함수
    public Bitmap rotateImage(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),src.getHeight(), matrix, true);
    }

    // Uri로 실제 물리경로 가져오기
    public String getRealPathFromURI(Uri contentUri){
        try{
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }catch (Exception e){
            e.printStackTrace();
            return contentUri.getPath();
        }
    }

}