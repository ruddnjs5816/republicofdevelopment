

(function ($) {

	getUserMe();
	getAllQuestionList();

	"use strict";


	$(window).stellar({
		responsive: true,
		parallaxBackgrounds: true,
		parallaxElements: true,
		horizontalScrolling: false,
		hideDistantElements: false,
		scrollProperty: 'scroll'
	});


	var fullHeight = function () {

		$('.js-fullheight').css('height', $(window).height());
		$(window).resize(function () {
			$('.js-fullheight').css('height', $(window).height());
		});

	};
	fullHeight();

	// loader
	var loader = function () {
		setTimeout(function () {
			if ($('#ftco-loader').length > 0) {
				$('#ftco-loader').removeClass('show');
			}
		}, 1);
	};
	loader();

	var carousel = function () {
		$('.carousel-testimony').owlCarousel({
			center: true,
			loop: true,
			items: 1,
			margin: 30,
			stagePadding: 0,
			nav: false,
			navText: ['<span class="ion-ios-arrow-back">', '<span class="ion-ios-arrow-forward">'],
			responsive: {
				0: {
					items: 1
				},
				600: {
					items: 2
				},
				1000: {
					items: 3
				}
			}
		});

	};
	carousel();

	$('nav .dropdown').hover(function () {
		var $this = $(this);
		// 	 timer;
		// clearTimeout(timer);
		$this.addClass('show');
		$this.find('> a').attr('aria-expanded', true);
		// $this.find('.dropdown-menu').addClass('animated-fast fadeInUp show');
		$this.find('.dropdown-menu').addClass('show');
	}, function () {
		var $this = $(this);
		// timer;
		// timer = setTimeout(function(){
		$this.removeClass('show');
		$this.find('> a').attr('aria-expanded', false);
		// $this.find('.dropdown-menu').removeClass('animated-fast fadeInUp show');
		$this.find('.dropdown-menu').removeClass('show');
		// }, 100);
	});


	$('#dropdown04').on('show.bs.dropdown', function () {
		console.log('show');
	});

	// magnific popup
	$('.image-popup').magnificPopup({
		type: 'image',
		closeOnContentClick: true,
		closeBtnInside: false,
		fixedContentPos: true,
		mainClass: 'mfp-no-margins mfp-with-zoom', // class to remove default margin from left and right side
		gallery: {
			enabled: true,
			navigateByImgClick: true,
			preload: [0, 1] // Will preload 0 - before current, and 1 after the current image
		},
		image: {
			verticalFit: true
		},
		zoom: {
			enabled: true,
			duration: 300 // don't foget to change the duration also in CSS
		}
	});

	$('.popup-youtube, .popup-vimeo, .popup-gmaps').magnificPopup({
		disableOn: 700,
		type: 'iframe',
		mainClass: 'mfp-fade',
		removalDelay: 160,
		preloader: false,

		fixedContentPos: false
	});


	var counter = function () {

		$('#section-counter').waypoint(function (direction) {

			if (direction === 'down' && !$(this.element).hasClass('ftco-animated')) {

				var comma_separator_number_step = $.animateNumber.numberStepFactories.separator(',')
				$('.number').each(function () {
					var $this = $(this),
						num = $this.data('number');
					console.log(num);
					$this.animateNumber(
						{
							number: num,
							numberStep: comma_separator_number_step
						}, 7000
					);
				});

			}

		}, { offset: '95%' });

	}
	counter();

	var contentWayPoint = function () {
		var i = 0;
		$('.ftco-animate').waypoint(function (direction) {

			if (direction === 'down' && !$(this.element).hasClass('ftco-animated')) {

				i++;

				$(this.element).addClass('item-animate');
				setTimeout(function () {

					$('body .ftco-animate.item-animate').each(function (k) {
						var el = $(this);
						setTimeout(function () {
							var effect = el.data('animate-effect');
							if (effect === 'fadeIn') {
								el.addClass('fadeIn ftco-animated');
							} else if (effect === 'fadeInLeft') {
								el.addClass('fadeInLeft ftco-animated');
							} else if (effect === 'fadeInRight') {
								el.addClass('fadeInRight ftco-animated');
							} else {
								el.addClass('fadeInUp ftco-animated');
							}
							el.removeClass('item-animate');
						}, k * 50, 'easeInOutExpo');
					});

				}, 100);

			}

		}, { offset: '95%' });
	};
	contentWayPoint();

	$('.appointment_date').datepicker({
		'format': 'm/d/yyyy',
		'autoclose': true
	});

	$('.appointment_time').timepicker();

})(jQuery);

function getUserMe() {
	var settings = {

		"url": "http://dewdew.shop:8080/users/mypage",
		"method": "GET",
		"timeout": 0,
		"headers": {
			"Authorization":
				localStorage.getItem('accessToken')
		},
	};

	$.ajax(settings).done(function (response) {
		console.log(response);
		console.log(status);
		// if(status ===403){
		// 	window.location = "/login.html"
		// }
		$('#loginUser').empty();
		$('#loginUser').append(response.username + '님 반갑습니다.')
	});
}






// // ----------------------------------------------------------------------------------------------------------------------

// 현재 페이지
let currentPage = 1;

// 페이지 버튼 클릭 시 이벤트 핸들러
$('.page-btn').on('click', function() {
  currentPage = parseInt($(this).data('page'));
  getAllQuestionList(currentPage, 6);
});

// 페이지 목록 생성 함수
function createPageButtons(totalPages) {
  let html = '';
  for (let i = 1; i <= totalPages; i++) {
    html += '<button class="page-btn" data-page="' + i + '">' + i + '</button>';
  }
  $('#page-buttons').html(html);
}

// 페이지 목록 초기화
function resetPageButtons() {
  $('#page-buttons').empty();
}

// 모든 질문 목록 불러오기 함수
function getAllQuestionList(page, size) {
  let settings = {
    "url": "http://dewdew.shop:8080/questions/all?page=" + page + "&size=" + size,
    "method": "GET",
    "timeout": 0,
    "headers": {
      "Authorization": localStorage.getItem('accessToken')
    },
  };

  $.ajax(settings).done(function(response) {
    console.log(response);
	$('#cards').empty();


    for (let i = 0; i < response.data.length; i++) {
      let questionList = response.data[i];
      let tempHtml = addAnswerHTML(questionList);
      $('#cards').append(tempHtml);
    }

    // 페이지 버튼 업데이트
    resetPageButtons();
    createPageButtons(response.totalPages);
    $('.page-btn[data-page="' + currentPage + '"]').addClass('active');
  });
}

// 초기 페이지 로드 시 첫 번째 페이지의 질문 목록 불러오기
getAllQuestionList(1, 6);

// ----------------------------------------------------------------------------------------------------------------------


	function addAnswerHTML(nickname, title, createdAt, answerCount) {
		let tempHtml = makeCard(nickname, title, createdAt, answerCount);
		$('#cards').append(tempHtml);
	  }

	function makeCard(questionList){

	return`
	<div class="blog-entry align-self-stretch">
	  <a onclick="goDetail(${questionList.questionId})" class="block-20 rounded" style="background-image: url('static/.vscode/imagesges/question_11.png');">
	  </a>
	  <div class="text p-4">
		  <div class="meta mb-2">
		  <div><a>${questionList.createdAt}</a></div>
		  <div><a>${questionList.nickname}</a></div>
		  <div><a class="meta-chat"><span class="fa fa-comment"></span> ${questionList.answerCount}</a></div>
		</div>
		<h3 class="heading"><span id="question0">${questionList.title}</span></h3>
	  </div>
	</div>`
  }





function goDetail(questionId)
 {
	 localStorage.setItem('currentQuestion',questionId)
	 window.location="../questionDetail.html"
 }

{/* <div class="col-md-4 d-flex ftco-animate">
	<div class="blog-entry align-self-stretch">
	  <a href="questionDetail.html" class="block-20 rounded" style="background-image: url('images/question_11.png');">
	  </a>
	  <div class="text p-4">
		  <div class="meta mb-2">
		  <div><a>${answer.createdAt}</a></div>
		  <div><a>${answer.nickName}</a></div>
		  <div><a class="meta-chat"><span class="fa fa-comment"></span> ${answer.answerCount}</a></div>
		</div>
		<h3 class="heading"><span id="question0">${answer.title}</span></h3>
	  </div>
	</div>
  </div> */}


function goSearch(searchTerm) {
	var searchTerm = document.querySelector('.searchTerm1').value;
	localStorage.setItem('searchTerm', searchTerm)
	window.location.href = '/search.html?searchTerm='+searchTerm
}