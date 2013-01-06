# How to create a virtual sdcard

(taken from: http://www.tylerfrankenstein.com/create-android-emulator-sd-card-and-write-data-to-it)

## Linux and MacOS ##


	$ cd ~/android-sdk-linux/tools
	$ ./mksdcard 64M ~/Desktop/sdcard.iso


## Windows

	cd C:\Program Files\android-sdk-windows\tools
	mksdcard 64M c:\documents and settings\tyler\desktop\sdcard.iso



# How to mount a virtual sdcard #

## Linux ##

	sudo mount -o loop ~/.android/avd/<myvirt>/sdcard.img <destdir>

## On MacOS ##

	$ hdiutil attach ~/.android/avd/Samsung_Nexus_S.avd/sdcard.img

## Windows ##

(taken from: http://stackoverflow.com/questions/1831440/is-there-a-way-to-mount-android-img-to-access-the-avd-android-virtual-device)

For Windows, I just ran across the ImDisk Virtual Disk Driver from http://www.ltr-data.se/opencode.html/#ImDisk. Install this utility, and you can then mount sdcard.img. There's a nice tutorial here: http://deltafalcon.com/2010/04/mounting-an-android-emulator-sd-card-image-in-windows/


# Controlling the Emulator #

cf. http://developer.android.com/tools/help/emulator.html#controlling
