<img width="700" alt="Capture" src="photo/main.png">

## Description

starTracker is an application that allows users to capture a photo or select an image from the gallery 
and it returns an image with stars surrounded by circles, with each star accompanied by its ID number. 
To find out the name of a star, users can enter its ID number and receive its corresponding name. 
All the photos are saved, allowing users to revisit the stars they have already discovered.


## Algorithm:
There are two parts to the algorithm:
### Identifying world coordinates system (WCS) and converting pixel coordinates to world coordinates (RA, DEC).
The algorithm used to identify the stars is based on the Plate Solution algorithm, implemented by the Astrometry.net project (https://astrometry.net/).
The algorithm receives an image and coordinates of the stars in the image in pixel units and returns the coordinates of the stars in the image in degrees.
The algorithm is based on the following steps:
1. The algorithm receives an image the coordinates of the stars in the image achieved by DAOStarFinder from  photoutils python library.
2. The algorithm finds the WCS of the image using blind astrometric solving without initial guess based on image metadata (e.g., time, location, orientation, etc.).
3. Iteratively, the algorithm finds the transformation matrix between the stars in the image and the stars in the catalog.
4. Update the WCS guess and repeat step 3 until the error is less than some fixed threshold.

### Matching the stars in the image to the stars in the catalog.
1. Convert the pixel coordinates of the stars in the image to world coordinates (RA, DEC).
2. Extract the stars from the catalog that are within a distance of 2.0 arcmin from the points found in the previous step. This is implemented using the "find_region" function from the Simbad Python library.
3. Find the stars in the catalog that are closest to the stars in the image by utilizing the k-d tree algorithm for efficient search. This step is implemented by the function "match_coordinates_sky" from the astropy Python library.


## Installation
To use the starTracker application, follow these steps:
1. Clone the repository's:<br />
  1.1 git clone https://github.com/einav242/starTracker.git<br />
  1.2 git clone https://github.com/einav242/project_new_space.git<br />
2. Open "starTracker" project in Android Studio.
3. Open the build.gradle file at the application level in  Android Studio and modify the following line:<br />
     python{<br />
              buildPython "C:/Users/e2402/AppData/Local/Microsoft/WindowsApps/python3.9.exe"<br />
        }<br />
to the Python path on your computer. <br />
4. Inside entities folder enter to the serverAPI file and change line 19:  <br />```.baseUrl(<your server network address>).client(okHttpClient).build();``` to your network ip address. <br />
5. Inside res-> xml enter to the network_security_config.xml file and add the following line:<br />
  ```<domain includeSubdomains="true"><your server network address></domain>```
6. Build and run the application on an Android device or emulator.<br />
7. In a separate open the project directory named "project_new_space" in a Python development environment, such as PyCharm and run the following command in the terminal to start the server:<br />
```uvicorn server:app --host <server network address> --port 8080 --reload``` <br />
or <br />
```python -m uvicorn server:app --host <server network address> --port 8080 --reload```<br />
**Note.** Verify that the phone and the server are connected to the same network (Wi-Fi, Ethernet, eg.)


## Main Functionality
<img width="200" src="photo/צילום מסך 2023-06-01 170642.png">
On the main screen, the user will be able to:

- **Add image–** After clicking on this button, you can choose an image from the gallery or take a photo yourself. 
Then, you can click on the "Algorithm" button to see the identified stars.
In the next window, you can choose to delete, save, or take another photo. If you select "save," you will proceed to the next window where you can enter the star's ID using the keyboard, and its corresponding name will be displayed next to it. <br />
Also, you can see the names of all the stars by clicking on the "Show all the names" button.
<img width="450" src="photo/תמונה2.png">

- **your discoverd stars–** By clicking on this button, you can view all the captured star images. You can delete an image by using the "Delete" button and find the names of the stars by clicking on the "Search by ID" button.
<img width="400" src="photo/תמונה1.png">


[![Watch the video](photo/צילום%20מסך%202023-06-05%20160338.png)](https://youtu.be/EIUpvY9gW8c)






