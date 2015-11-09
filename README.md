# JoystickView 
A JoystickView library for Android with an example

Joystick controller for android applications.

*Simple usage

*Callback interface implemented.

*Power and degree values returned.

*Changable assets.You can change joystick images as you wish.


From xml add the lines below:
XML:

        <com.developwear.joystickview.JoystickView
        android:id="@+id/joystickview"
        android:layout_width="500px"
        android:layout_height="500px" />

![](https://github.com/clkasd/JoystickView/blob/master/readmeImages/Image_1447056442984.png)

And from your java class set joystickChangeListener:

        joystickView=(JoystickView)findViewById(R.id.joystickview);
        joystickView.setJoystickChangeListener(new JoystickView.JoystickChangeListener() {
            @Override
            public void onJoystickChanged(int power, int degree) {
                textView.setText("power:".concat(String.valueOf(power).concat(" - ")).concat("degree:").concat(String.valueOf(degree)));
            }
        });

![](https://github.com/clkasd/JoystickView/blob/master/readmeImages/2015-11-09%2008.04.34.png)

# Installation
You can either add as a module or directly add to build.gradle file as shown below:

        compile 'com.developwear:joystickviewlib:1.0.1'

