/** @format */

writeOutBooks();

const button = document.querySelector("#add");



button.addEventListener("click", () => {
	let title = document.querySelector("#title").value;
	let author = document.querySelector("#author").value;
	let number = document.querySelector("#yearOfRelease").value * 1;

	let bookToSave = {
		title: title,
		author: author,
		yearOfRelease: number,
	};

	console.log("Sending data: " + JSON.stringify(bookToSave));

	fetch("http://localhost:8080/books/create", {
		method: "POST",
		body: JSON.stringify(bookToSave),
		headers: {
			"Content-type": "application/json; charset=UTF-8",
		},
	})
		.then((response) => {
			if (response.ok) {
				writeOutBooks();
			} else {
				return Promise.reject(response);
			}
		})
		.catch((error) => {
			console.warn("Something went wrong.", error);
		});
		getCount();
	 
	// $.ajax({
	//     type: "POST",
	//     url: "/books/create",
	//     contentType: 'application/json;charset=UTF-8',
	//     data: JSON.stringify(bookToSave),
	//     success: function (data) {
	//         console.log("Response from server: " + JSON.stringify(data))
	//         writeOutBooks();
	//     }
	// });
});

async function writeOutBooks() {
	const booksTable = document.querySelector("#books");
	const thead = document.querySelector("#thead").content.cloneNode(true);
	while (booksTable.firstChild) {
		booksTable.removeChild(booksTable.firstChild);
	}

	booksTable.appendChild(thead);

	const response = await fetch("http://localhost:8080/books/read");
	const books = await response.json();
	console.log(books);

	books.forEach((book) => {
		const templateCopy = document.querySelector("#row").content.cloneNode(true);

		const idDiv = templateCopy.querySelector(".id");
		const titleDiv = templateCopy.querySelector(".title");
		const authorDiv = templateCopy.querySelector(".author");
		const yearDiv = templateCopy.querySelector(".year");
		const deleteBtn = templateCopy.querySelector(".delete");
		const updateBtn = templateCopy.querySelector(".update");

		deleteBtn.addEventListener("click", () => {
			deleteBook(book.id);
		});

		updateBtn.addEventListener("click", () => {
			console.log("t");
		});

		if (book.id !== null) {
			idDiv.textContent = book.id;
			titleDiv.textContent = book.title;
			authorDiv.textContent = book.author;
			yearDiv.textContent = book.yearOfRelease;
		}

		booksTable.appendChild(templateCopy);
	});

	function deleteBook(id) {
		console.log("test");
		fetch("http://localhost:8080/books/delete", {
			method: "POST",
			// mode: "no-cors",
			body: JSON.stringify({
				id: id,
				title: "title",
				author: "author",
				yearOfRelease: 0,
			}),
			headers: {
				"Content-type": "application/json; charset=UTF-8",
			},
		})
			.then((response) => {
				if (response.ok) {
					writeOutBooks();
				} else {
					return Promise.reject(response);
				}
			})
			.catch((error) => {
				console.warn("Something went wrong.", error);
			});
	}
}

async function getCount() {
	const response = await fetch("http://localhost:8080/books/getCount");
	const books = await response.json();
	
	console.log(books.numOfRecords);
}