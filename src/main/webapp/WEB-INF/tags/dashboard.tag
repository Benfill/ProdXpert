<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ tag import="entity.User"%>
<%
User loggedInUser = null;
if (session != null) {
	loggedInUser = (User) session.getAttribute("loggedInUser");
}
%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/style.css">
<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/flowbite@2.5.2/dist/flowbite.min.js"></script>
</head>
<body>
	<div>
		<script
			src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.x.x/dist/alpine.min.js"
			defer></script>

		<div x-data="{ sidebarOpen: false }" class="flex h-screen bg-gray-200">
			<div :class="sidebarOpen ? 'block' : 'hidden'"
				@click="sidebarOpen = false"
				class="fixed inset-0 z-20 transition-opacity bg-black opacity-50 lg:hidden"></div>

			<div
				:class="sidebarOpen ? 'translate-x-0 ease-out' : '-translate-x-full ease-in'"
				class="fixed inset-y-0 left-0 z-30 w-64 overflow-y-auto transition duration-300 transform bg-gray-900 lg:translate-x-0 lg:static lg:inset-0">
				<div class="flex items-center justify-center mt-8">
					<div class="flex items-center">
						<svg class="w-12 h-12" viewBox="0 0 512 512" fill="none"
							xmlns="http://www.w3.org/2000/svg">
                        <path
								d="M364.61 390.213C304.625 450.196 207.37 450.196 147.386 390.213C117.394 360.22 102.398 320.911 102.398 281.6C102.398 242.291 117.394 202.981 147.386 172.989C147.386 230.4 153.6 281.6 230.4 307.2C230.4 256 256 102.4 294.4 76.7999C320 128 334.618 142.997 364.608 172.989C394.601 202.981 409.597 242.291 409.597 281.6C409.597 320.911 394.601 360.22 364.61 390.213Z"
								fill="#4C51BF" stroke="#4C51BF" stroke-width="2"
								stroke-linecap="round" stroke-linejoin="round"></path>
                        <path
								d="M201.694 387.105C231.686 417.098 280.312 417.098 310.305 387.105C325.301 372.109 332.8 352.456 332.8 332.8C332.8 313.144 325.301 293.491 310.305 278.495C295.309 263.498 288 256 275.2 230.4C256 243.2 243.201 320 243.201 345.6C201.694 345.6 179.2 332.8 179.2 332.8C179.2 352.456 186.698 372.109 201.694 387.105Z"
								fill="white"></path>
                    </svg>

						<span class="mx-2 text-2xl font-semibold text-white">Dashboard</span>
					</div>
				</div>
				<nav class="mt-10">
					<a
						class="flex items-center px-6 py-2 mt-4 text-gray-500 hover:bg-gray-700 hover:bg-opacity-25 hover:text-gray-100"
						href="${ pageContext.request.contextPath }/user?action=new"> <svg
							class="w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none"
							viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round"
								stroke-linejoin="round" stroke-width="2"
								d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z">
                        </path>
                    </svg> <span class="mx-3">Create User</span>
					</a> <a
						class="flex items-center px-6 py-2 mt-4 text-gray-500 hover:bg-gray-700 hover:bg-opacity-25 hover:text-gray-100"
						href="${ pageContext.request.contextPath }/article?action=admin&filter=ALL">
						<svg class="w-6 h-6" xmlns="http://www.w3.org/2000/svg"
							fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round"
								stroke-linejoin="round" stroke-width="2"
								d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z">
                        </path>
                    </svg> <span class="mx-3">Articles</span>
					</a> 
					<a
						class="flex items-center px-6 py-2 mt-4 text-gray-500 hover:bg-gray-700 hover:bg-opacity-25 hover:text-gray-100"
						href="${ pageContext.request.contextPath }/user"> <svg
							class="w-6 h-6" viewBox="0 0 24 24" fill="none"
							xmlns="http://www.w3.org/2000/svg">
							<g id="SVGRepo_bgCarrier" stroke-width="0"></g>
							<g id="SVGRepo_tracerCarrier" stroke-linecap="round"
								stroke-linejoin="round"></g>
							<g id="SVGRepo_iconCarrier"> <path
								d="M13 20V18C13 15.2386 10.7614 13 8 13C5.23858 13 3 15.2386 3 18V20H13ZM13 20H21V19C21 16.0545 18.7614 14 16 14C14.5867 14 13.3103 14.6255 12.4009 15.6311M11 7C11 8.65685 9.65685 10 8 10C6.34315 10 5 8.65685 5 7C5 5.34315 6.34315 4 8 4C9.65685 4 11 5.34315 11 7ZM18 9C18 10.1046 17.1046 11 16 11C14.8954 11 14 10.1046 14 9C14 7.89543 14.8954 7 16 7C17.1046 7 18 7.89543 18 9Z"
								stroke="#6b6b6b" stroke-width="2" stroke-linecap="round"
								stroke-linejoin="round"></path> </g></svg> <span class="mx-3">Users</span>
					</a> <a
						class="flex items-center px-6 py-2 mt-4 text-gray-500 hover:bg-gray-700 hover:bg-opacity-25 hover:text-gray-100"
						href="${ pageContext.request.contextPath }/comment"> <svg
							class="w-6 h-6" viewBox="-0.5 0 32 32" version="1.1"
							xmlns="http://www.w3.org/2000/svg"
							xmlns:xlink="http://www.w3.org/1999/xlink"
							xmlns:sketch="http://www.bohemiancoding.com/sketch/ns"
							fill="#ffffff">
							<g id="SVGRepo_bgCarrier" stroke-width="0"></g>
							<g id="SVGRepo_tracerCarrier" stroke-linecap="round"
								stroke-linejoin="round"></g>
							<g id="SVGRepo_iconCarrier"> <title>comments</title> <desc>Created with Sketch Beta.</desc> <defs> </defs> <g
								id="Page-1" stroke="none" stroke-width="1" fill="none"
								fill-rule="evenodd" sketch:type="MSPage"> <g id="Icon-Set"
								sketch:type="MSLayerGroup"
								transform="translate(-257.000000, -255.000000)" fill="#6e6e6e"> <path
								d="M259,266.5 C259,261.254 263.753,257 270,257 C274.973,257 280,261.254 280,266.5 C280,271.747 276.075,276 270,276 C269.107,276 267.244,275.898 266.413,275.725 L263,278 L263,274.456 C260.561,272.477 259,269.834 259,266.5 L259,266.5 Z M266.637,277.736 C267.414,277.863 269.181,278 270,278 C277.18,278 282,272.657 282,266.375 C282,260.093 275.977,255 270,255 C262.811,255 257,260.093 257,266.375 C257,270.015 258.387,273.104 261,275.329 L261,281 L266.637,277.736 L266.637,277.736 Z M283.949,264.139 C283.968,264.425 284,264.709 284,265 C284,265.636 283.938,266.259 283.849,266.874 C285.195,268.45 286,270.392 286,272.5 C286,275.834 284.008,278.761 281,280.456 L281,284 L277.587,281.725 C276.756,281.898 275.893,282 275,282 C272.41,282 271.034,281.222 269.154,279.929 C268.609,279.973 268.059,280 267.5,280 C267.102,280 266.712,279.972 266.32,279.949 C268.701,282.276 271.149,283.75 275,283.75 C275.819,283.75 276.618,283.676 277.395,283.549 L283,287 L283,281.329 C286.04,279.246 288,276.015 288,272.375 C288,269.131 286.439,266.211 283.949,264.139 L283.949,264.139 Z M275.5,268 C276.329,268 277,267.329 277,266.5 C277,265.672 276.329,265 275.5,265 C274.671,265 274,265.672 274,266.5 C274,267.329 274.671,268 275.5,268 L275.5,268 Z M263.5,268 C264.329,268 265,267.329 265,266.5 C265,265.672 264.329,265 263.5,265 C262.671,265 262,265.672 262,266.5 C262,267.329 262.671,268 263.5,268 L263.5,268 Z M269.5,268 C270.329,268 271,267.329 271,266.5 C271,265.672 270.329,265 269.5,265 C268.671,265 268,265.672 268,266.5 C268,267.329 268.671,268 269.5,268 L269.5,268 Z"
								id="comments" sketch:type="MSShapeGroup"> </path> </g> </g> </g></svg> <span
						class="mx-3">Comments</span>
					</a> <a
						class="flex items-center px-6 py-2 mt-4 text-gray-500 hover:bg-gray-700 hover:bg-opacity-25 hover:text-gray-100"
						href="${ pageContext.request.contextPath }"> <svg
							class="w-6 h-6" version="1.1" id="Layer_1"
							xmlns="http://www.w3.org/2000/svg"
							xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 32 32"
							enable-background="new 0 0 32 32" xml:space="preserve"
							fill="#ffffff">
							<g id="SVGRepo_bgCarrier" stroke-width="0"></g>
							<g id="SVGRepo_tracerCarrier" stroke-linecap="round"
								stroke-linejoin="round"></g>
							<g id="SVGRepo_iconCarrier"> <g> <path fill="#737373"
								d="M30.5,0h-29C0.673,0,0,0.673,0,1.5v8C0,10.327,0.673,11,1.5,11h29c0.827,0,1.5-0.673,1.5-1.5v-8 C32,0.673,31.327,0,30.5,0z M31,9.5c0,0.275-0.225,0.5-0.5,0.5h-29C1.225,10,1,9.775,1,9.5v-8C1,1.225,1.225,1,1.5,1h29 C30.775,1,31,1.225,31,1.5V9.5z"></path> <path
								fill="#737373"
								d="M31.5,12.5c-0.276,0-0.5,0.224-0.5,0.5v17.5c0,0.275-0.225,0.5-0.5,0.5h-29C1.225,31,1,30.775,1,30.5V13 c0-0.276-0.224-0.5-0.5-0.5S0,12.724,0,13v17.5C0,31.327,0.673,32,1.5,32h29c0.827,0,1.5-0.673,1.5-1.5V13 C32,12.724,31.776,12.5,31.5,12.5z"></path> <path
								fill="#737373"
								d="M13.5,27c0.827,0,1.5-0.673,1.5-1.5v-8c0-0.827-0.673-1.5-1.5-1.5h-8C4.673,16,4,16.673,4,17.5v8 C4,26.327,4.673,27,5.5,27H13.5z M5,25.5v-8C5,17.225,5.225,17,5.5,17h8c0.275,0,0.5,0.225,0.5,0.5v8c0,0.275-0.225,0.5-0.5,0.5h-8 C5.225,26,5,25.775,5,25.5z"></path> <path
								fill="#737373"
								d="M18,18h9c0.276,0,0.5-0.224,0.5-0.5S27.276,17,27,17h-9c-0.276,0-0.5,0.224-0.5,0.5S17.724,18,18,18z"></path> <path
								fill="#737373"
								d="M18,22h9c0.276,0,0.5-0.224,0.5-0.5S27.276,21,27,21h-9c-0.276,0-0.5,0.224-0.5,0.5S17.724,22,18,22z"></path> <path
								fill="#737373"
								d="M18,26h9c0.276,0,0.5-0.224,0.5-0.5S27.276,25,27,25h-9c-0.276,0-0.5,0.224-0.5,0.5S17.724,26,18,26z"></path> </g> </g></svg>
						<span class="mx-3">Home Page</span>
					</a>
				</nav>
			</div>
			<div class="flex flex-col flex-1 overflow-hidden">
				<header
					class="flex items-center justify-between px-6 py-4 bg-white border-b-4 border-indigo-600">
					<div class="flex items-center">
						<button @click="sidebarOpen = true"
							class="text-gray-500 focus:outline-none lg:hidden">
							<svg class="w-6 h-6" viewBox="0 0 24 24" fill="none"
								class="w-6 h-6" xmlns="http://www.w3.org/2000/svg">
                            <path d="M4 6H20M4 12H20M4 18H11"
									stroke="currentColor" stroke-width="2" stroke-linecap="round"
									stroke-linejoin="round"></path>
                        </svg>
						</button>

						<div class="relative mx-4 lg:mx-0">
							<span class="absolute inset-y-0 left-0 flex items-center pl-3">
								<svg class="w-5 h-5 text-gray-500" viewBox="0 0 24 24"
									fill="none">
                                <path
										d="M21 21L15 15M17 10C17 13.866 13.866 17 10 17C6.13401 17 3 13.866 3 10C3 6.13401 6.13401 3 10 3C13.866 3 17 6.13401 17 10Z"
										stroke="currentColor" stroke-width="2" stroke-linecap="round"
										stroke-linejoin="round">
                                </path>
                            </svg>
							</span> <input
								class="w-32 pl-10 pr-4 rounded-md form-input sm:w-64 focus:border-indigo-600"
								type="text" placeholder="Search">
						</div>
					</div>

					<div class="flex items-center">
						<c:choose>
							<c:when test="${ not empty loggedInUser }">
								<p class="text-sm mr-4 text-gray-800">Welcome back
									${loggedInUser.firstName} ${loggedInUser.secondName}!</p>
							</c:when>
							<c:otherwise>
								<p>must never get here.</p>
							</c:otherwise>
						</c:choose>
						<div x-data="{ dropdownOpen: false }" class="relative">
							<button @click="dropdownOpen = ! dropdownOpen"
								class="relative block w-8 h-8 overflow-hidden rounded-full shadow focus:outline-none">
								<img class="object-cover w-full h-full"
									src="https://images.unsplash.com/photo-1528892952291-009c663ce843?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=crop&amp;w=296&amp;q=80"
									alt="Your avatar">
							</button>

							<div x-show="dropdownOpen" @click="dropdownOpen = false"
								class="fixed inset-0 z-10 w-full h-full" style="display: none;"></div>

							<div x-show="dropdownOpen"
								class="absolute right-0 z-10 w-48 mt-2 overflow-hidden bg-white rounded-md shadow-xl"
								style="display: none;">
								<div>
									<form class="flex justify-content-start"
										action="auth?action=logout" method="post">
										<button type="submit">Logout</button>
									</form>
								</div>
							</div>
						</div>
					</div>
				</header>
				<jsp:doBody />

			</div>
		</div>
	</div>
</body>
</html>