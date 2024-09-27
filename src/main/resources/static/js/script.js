console.log("Script loaded");

// change theme work
let currentTheme = getTheme();

//initial -->
document.addEventListener("DOMContentLoaded", () => {
	changeTheme();
});

//TODO:
function changeTheme() {
	//set to web page

	changePageTheme(currentTheme, "");
	//set the listener to change theme button
	const changeThemeButton = document.querySelector("#theme_change_button");

	changeThemeButton.addEventListener("click", (event) => {
		let oldTheme = currentTheme;
		console.log("change theme button clicked");
		if (currentTheme === "dark") {
			//theme ko light
			currentTheme = "light";
		} else {
			//theme ko dark
			currentTheme = "dark";
		}
		console.log(currentTheme);
		changePageTheme(currentTheme, oldTheme);
	});
}

//set theme to localstorage
function setTheme(theme) {
	localStorage.setItem("theme", theme);
}

//get theme from localstorage
function getTheme() {
	let theme = localStorage.getItem("theme");
	return theme ? theme : "light";
}

//change current page theme
function changePageTheme(theme, oldTheme) {
	//localstorage mein update karenge
	setTheme(currentTheme);
	//remove the current theme

	if (oldTheme) {
		document.querySelector("html").classList.remove(oldTheme);
	}
	//set the current theme
	document.querySelector("html").classList.add(theme);

	// change the text of button
	document
		.querySelector("#theme_change_button")
		.querySelector("span").textContent = theme == "light" ? "Dark" : "Light";
}
//change page change theme

// Change color of navbar...............
// Get all links
const links = document.querySelectorAll('#navbar-cta ul li a');

// Get the current URL path
const currentPath = window.location.pathname;

// Add active class to the link that matches the current URL path
links.forEach((link) => {
	if (link.getAttribute('href') === currentPath) {
		link.classList.add('active');
	}
});



// User Picture Shown in Register Details ............
/*const defaultCarouselSelect = document.getElementById('default-carousel');
document
	.querySelector("#image_file_input")
	.addEventListener("change", function(event) {
		let files = event.target.files;
		let preview = document.getElementById("image_preview");
		let previewContainer = document.getElementById("image_preview_container");

		if (files.length > 0) { // corrected condition
			previewContainer.style.display = 'block';
			defaultCarouselSelect.style.display = 'none';
		} else {
			previewContainer.style.display = 'none';
			defaultCarouselSelect.style.display = 'block';
		}

		// clear previous images
		preview.innerHTML = '';

		for (let i = 0; i < files.length; i++) {
			let file = files[i];
			let reader = new FileReader();
			reader.onload = function() {
				let img = document.createElement("img");
				img.src = reader.result;
				// img.className = "inline-flex flex-col justify-center space-x-3 sm:w-20 sm:h-20 md:w-32 md:h-32  xl:w-52 xl:h-52 m-2 rounded-xl shadow";
				img.className = "inline-flex flex-col justify-center space-x-3 w-40 md:w-1/3 lg:w-1/6 xl:w-1/8 p-2 rounded-xl shadow";
				preview.appendChild(img);
			};
			reader.readAsDataURL(file);
		}
	});
*/

const defaultCarouselSelect = document.getElementById('default-carousel');
document.querySelector("#image_file_input").addEventListener("change", function(event) {
	let files = event.target.files;
	let preview = document.getElementById("image_preview");
	let previewContainer = document.getElementById("image_preview_container");

	if (files.length > 0) {
		previewContainer.style.display = 'block';
		if (defaultCarouselSelect) {
			defaultCarouselSelect.style.display = 'none';
		}
	} else {
		previewContainer.style.display = 'none';
		if (defaultCarouselSelect) {
			defaultCarouselSelect.style.display = 'block';
		}
	}

	// clear previous images
	preview.innerHTML = '';

	for (let i = 0; i < files.length; i++) {
		let file = files[i];
		let reader = new FileReader();
		reader.onload = function(e) {
			let img = document.createElement("img");
			img.src = e.target.result;
			img.className = "w-40 md:w-1/3 lg:w-1/6 xl:w-1/8 p-2 rounded-xl shadow";
			img.alt = `Preview Image ${i + 1}`;
			preview.appendChild(img);
		};
		reader.readAsDataURL(file);
	}
});

