package com.example.android.sceneformagimg;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;

import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.QuaternionEvaluator;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.math.Vector3Evaluator;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

import java.util.Random;

class RotatingNode extends TransformableNode {

    private String TAG = "RotatingNode";

    private ObjectAnimator mObjectAnimator;

    private float X = 1f;
    private float Y = 1f;
    private float Z= 1f;

    public RotatingNode(TransformationSystem transformationSystem) {
        super(transformationSystem);
    }

    @Override
    public void onUpdate(FrameTime frameTime) {
        super.onUpdate(frameTime);
//         mObjectAnimator.resume();
    }

    @Override
    public void onActivate() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translate(), rotate());
        animatorSet.start();
    }

    private ObjectAnimator translate() {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        Vector3[] sides = new Vector3[2];
        sides[0] = new Vector3(0.08f, -0.08f,0f);
        sides[1] = new Vector3(-0.08f, 0f,0);
        objectAnimator.setTarget(this);
        objectAnimator.setPropertyName("localPosition");
        objectAnimator.setObjectValues((Object[]) sides);
        objectAnimator.setDuration(1000);
        objectAnimator.setEvaluator(new Vector3Evaluator());
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setAutoCancel(true);
        return objectAnimator;
    }

    private ObjectAnimator rotate() {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        Quaternion[] orientations = new Quaternion[4];
        // Rotation to apply first, to tilt its axis.
        Quaternion baseOrientation = Quaternion.axisAngle(new Vector3(1.0f, 0f, 0.0f), 10f);
        for (int i = 0; i < orientations.length; i++) {
            float angle = i * 360 / (orientations.length - 1);
            Quaternion orientation = Quaternion.axisAngle(new Vector3(1.0f, 0.0f, 0.0f), angle);
            orientations[i] = orientation;
//            orientations[i] = Quaternion.multiply(baseOrientation, orientation);
        }
        objectAnimator.setTarget(this);
        objectAnimator.setDuration((long) (1000 * 360 / (90)));
        objectAnimator.setPropertyName("localRotation");
        objectAnimator.setObjectValues((Object[]) orientations);
        objectAnimator.setEvaluator(new QuaternionEvaluator());
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setAutoCancel(true);
        return objectAnimator;
    }




}
