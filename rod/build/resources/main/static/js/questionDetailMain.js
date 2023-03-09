

(function ($) {

	getUserMe();
	// getAnswerCount();
	// getQuestionDetail();

	// $('#questionIsSelected').hide();
	$('#questionIsSelected').show();

	// getLikeStatus();
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

function signOut() {
	console.log("logout진행중");
    localStorage.removeItem('accessToken');
	alert("로그아웃이 완료되었습니다.")

	window.location = '/index.html'

}

//질문 개별 조회 시 답변 개수 확인 (ex. N개의 답변이 있습니다.)
// function getAnswerCount() {
// 	var settings = {
// 		"url": "http://dewdew.shop:8080/questions/all",
// 		"method": "GET",
// 		"timeout": 0,
// 		"headers": {
// 		  "Authorization": 
// 		  localStorage.getItem('accessToken')
// 		},
// 		};
		
// 		$.ajax(settings).done(function (response) {
// 		console.log(response);
// 		console.log(response.data[0].answerCount);
// 		$('#answerCount').empty();
// 		for (i = 0; i < response.data.length; i++) {
// 			if(response.data[i].answerCount === )
//   			$('#answerCount').append(response.data[i].answerCount + '개의 답변이 있습니다.');
// 		}
		
// 	});
// }

//------------------------------------------------------------------------------------------------

// 현재 페이지
let currentPage = 1;

// 페이지 버튼 클릭 시 이벤트 핸들러
$('.page-btn').on('click', function() {
	currentPage = parseInt($(this).data('page'));
	getQuestionDetail(currentPage, 5);
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



//질문/답변/댓글 한꺼번에 개별 조회
function getQuestionDetail(page, size) {

	var settings = {
		"url": 
		"http://dewdew.shop:8080/questions/specific/"+localStorage.getItem('currentQuestion') + "?page=" + page + "&size=" + size,
		"method": "GET",
		"timeout": 0,
		"headers": {
			"Authorization": localStorage.getItem('accessToken'),
		},
	};

	$.ajax(settings).done(function (response) {

		$('#answer-box').empty();

		console.log(response)
		console.log(response.title);
		console.log(response.createdAt);
		console.log(response.difficulty);
		console.log(response.totalAnswerCount);
		console.log(response)
		$('#nickname').empty();
		$('#nickname').append(response.nickname);
		$('#createdAt').empty();
		$('#createdAt').append(response.createdAt);
		$('#difficulty').empty();
		$('#difficulty').append('난이도 : '+response.difficulty);
		$('#title').empty();
		$('#title').append(response.title);
		$('#content').empty();
		$('#content').append(response.content);
		$('#tag-box').empty();


		console.log(response.tagList.hashTags[0])
		for(let i=0; i<response.tagList.hashTags.length; i++) {
			let tag = response.tagList.hashTags[i];
			let tagHtml = addTagHtml(tag);
			$('#tag-box').append(tagHtml);
		}

		
		

		$('#totalAnswerCount').empty();
		$('#totalAnswerCount').append(response.totalAnswerCount +' 개의 답변이 있습니다.');
		
		$('#isSelected-box').empty();
		console.log(response.answerWithComments[0].selected)
		


		// $('#answerContent').empty();
		// $('#answerContent').append(response.answerWithComments[0].content);
		// $('#getComment').empty();
		// $('#getComment').append(response.answerWithComments[0].commentResponseDtoList[0].content);
		console.log(response.answerWithComments);
		console.log(response.answerWithComments.length);
		console.log(response.answerWithComments[0].commentResponseDtoList.length);
		for (let i = 0; i < response.answerWithComments.length; i++) {
			console.log('test')
			let answer = response.answerWithComments[i];
			console.log("answer id" + answer.answerId);
			let tempHtml1 = addAnswerHTML(answer.answerId, answer.nickname, answer.content, answer.createdAt, answer.likes);
			console.log(tempHtml1);
			$('#answer-box').append(tempHtml1);
			console.log(response.answerWithComments[i].commentResponseDtoList)


			if(answer.selected === true) {
				let tempHtml = addAnswerIsSelectedHTML(answer.answerId, answer.nickname, answer.content, answer.createdAt, answer.likes);
				console.log(tempHtml)
				$('#isSelected-box').append(tempHtml);
			}


			for (let j = 0; j < response.answerWithComments[i].commentResponseDtoList.length; j++) {
				console.log('test2')
				let comment = response.answerWithComments[i].commentResponseDtoList[j];
				let tempHtml2 = addCommentHTML(answer.answerId, comment.commentId, comment.nickName, comment.content, comment.createdAt);
				console.log(tempHtml2);
				console.log("comment answer id" + answer.answerId);
				$('#'+answer.answerId+'-comment-box').append(tempHtml2)
				console.log("comment" + comment);
			}
		}

	});
}


getQuestionDetail(1, 5);


//------------------------------------------------------------------------------------------------
function addTagHtml(tag) {
	let tagHtml = makeTagHtml(tag) 
	$('#tag-box').append(tagHtml);

}

function makeTagHtml(tag) {
	return  `<a href="#" class="tag-cloud-link" id="oneTag">${tag}</a>`;
}















//답변 등록
function answerSummit() {
	var settings = {
		"url": "http://dewdew.shop:8080/api/questions/"+localStorage.getItem('currentQuestion')+"/answers",
		"method": "POST",
		"timeout": 0,
		"headers": {
			"Authorization": localStorage.getItem('accessToken'),
			"Content-Type": "application/json"
		},
		"data": JSON.stringify({
			"content": $('#summernote').val(),
			"difficulty": parseFloat($('#inPutDifficulty').val())
		}),
	};

	$.ajax(settings).done(function (response) {
		console.log(response);
		localStorage.setItem('answerId', response.answerId) ;//DB같은 역할
		alert("답변등록이 완료되었습니다.")
		window.location = '/questionDetail.html'

	}).fail(function(response){
		console.log(response.status)
		if(response.status === 500){
		alert('본인 글에는 답변 할 수 없습니다.')
		window.location = '/questionDetail.html'
		}else{
		alert('서버에 문제가 발생하였습니다.')
		}
	});
}

//댓글 등록
function commentSummit(answerId) {
	var settings = {
		"url": "http://dewdew.shop:8080/api/questions/answers/"+answerId+"/comments",
		"method": "POST",
		"timeout": 0,
		"headers": {
			"Authorization": localStorage.getItem('accessToken'),
			"Content-Type": "application/json"
		},
		"data": JSON.stringify({
			"content": $('#'+answerId+'-comment').val()
		}),  
	};

	$.ajax(settings).done(function (response) {
		console.log(response);
		localStorage.setItem('commentId', response.commentId);//DB같은 역할
		alert("댓글등록이 완료되었습니다.")
		window.location = '/questionDetail.html'
	});
}



// 답변과 댓글을 불러와서 보여줌.
/**
 * 답변 목록: #answer-box
 * 댓글 목록: #comment-box
 * 답변 HTML 만드는 함수: addAnswerHTML
 * 댓글 HTML 만드는 함수: addCommentHTML
   */

//답변 하나를 HTML로 만들어서 body 태그 내 원하는 곳에 붙입니다.
function addAnswerHTML(answerId, nickname, content, createdAt, likes) {
	let tempHtml1 = makeAnswer(answerId, nickname, content, createdAt, likes)
	return tempHtml1
}

function makeAnswer(answerId, nickname, content, createdAt, likes) {
	return `<li class="comment" id="${answerId}">
          <div class="vcard bio">
          <img src="images/기본프로필.jpg" alt="Image placeholder">
          </div>
          <div class="comment-body">
          <h3>${nickname}
		  	<a id="${answerId}-submit" onclick="submitEditAnswer(${answerId})" class="w3-bar-item w3-button" style="color: gray; padding: 4px; font-size: 14px; float:right; margin-left: 4px; display: none"><i class="fa fa-pencil-square-o" aria-hidden="true "> 수정완료</i></a>
            <a id="${answerId}-edit" onclick="editAnswer(${answerId})" class="w3-bar-item w3-button" style="color: gray; padding: 4px; font-size: 14px; float:right; margin-left: 4px"><i class="fa fa-pencil-square-o" aria-hidden="true"> 수정</i></a>
            <a id="${answerId}-delete" onclick="deleteAnswer(${answerId})" class="w3-bar-item w3-button" style="color: gray; padding: 4px; font-size: 14px; float:right;"><i class="fa fa-trash-o" aria-hidden="true"> 삭제</i></a>
          </h3>
          <div class="meta">${createdAt}
            
          </div>
          <p id="${answerId}-answerContent" class="text">${content}</p>
		  <div id="${answerId}-editarea" class="edit">
                <textarea id="${answerId}-answertextarea" class="form-control1" name="" cols="70" rows="10" style="display: none" placeholder="수정할 답변을 입력해주세요"></textarea>
        	</div>
          <p><button type="button" class="reply" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">Reply</button>
            <div class="comment-group" class="collapse" id="collapseExample">
            <div class="comment-form">
              <textarea class="form-control1" placeholder="댓글을 입력해주세요" id="${answerId}-comment"></textarea>
              <button type="button" onclick="commentSummit(${answerId})" class="registerCommentBtn" style="color:black; background-color: #deb887; border: none; font-weight: 400; font-size: 13px; letter-spacing: .05em; padding:2px 10px; border-radius: 4px; float: right; margin-top: 5px;">등록</button>
            </div>
            </div>
            <button id="${answerId}-isSelected" type="button" onclick="answerIsSelected(${answerId})" class="reply" style="border: none; background-color:#deb887; float:right;">채택하기</button>
            <button onclick="likeAnswer(${answerId})" id="${answerId}-like-button" class="nav-icon-btn like" type="button" style="border: none; color: #deb887; float:right;" ><span class="fa fa-heart-o" style="font-size: 16px; font-weight: 650;"></span> ${likes}</button>
          </p>
		  <ul class="children" id="${answerId}-comment-box">
        	</ul>`;
}







function addCommentHTML(answerId, commentId, nickName, content, createdAt) {
	let tempHtml2 = makeComment(answerId, commentId, nickName, content, createdAt);
	return tempHtml2
}

function makeComment(answerId, commentId, nickName, content, createdAt) {
	return `<li class="comment" >
				<div class="vcard bio">
				<img src="images/기본프로필.jpg" alt="Image placeholder">
				</div>
				<div class="comment-body">
				<h3>${nickName}
					<a id="${commentId}-commentSubmit" onclick="submitEditComment(${answerId}, ${commentId})"  class="w3-bar-item w3-button" style="color: gray; padding: 4px; font-size: 14px; float:right; margin-left: 4px; display: none"><i class="fa fa-pencil-square-o" aria-hidden="true "> 수정완료</i></a>
					<a id="${commentId}-commentEdit" onclick="editComment(${answerId}, ${commentId})" class="w3-bar-item w3-button" style="color: gray; padding: 4px; font-size: 14px; float:right; margin-left: 4px"><i class="fa fa-pencil-square-o" aria-hidden="true"> 수정</i></a>
            		<a id="${commentId}-commentDelete" onclick="deleteComment(${answerId}, ${commentId})" class="w3-bar-item w3-button" style="color: gray; padding: 4px; font-size: 14px; float:right;"><i class="fa fa-trash-o" aria-hidden="true"> 삭제</i></a>
				</h3>
				<div class="meta">${createdAt}</div>
				<p id="${answerId}-comment" class="text">${content}</p>
				<div id="${commentId}-editarea" class="edit">
                <textarea id="${commentId}-commenttextarea" class="form-control1" name="" cols="70" rows="10" style="display: none" placeholder="수정할 댓글을 입력해주세요"></textarea>
        		</div>
				</div>
        	</li>`;
}

//질문 삭제
function deleteQuestion() {
	var settings = {
		"url": "http://dewdew.shop:8080/questions/"+localStorage.getItem('currentQuestion'),
		"method": "DELETE",
		"timeout": 0,
		"headers": {
			"Authorization": localStorage.getItem('accessToken'),
		},
	  };
	  
	  $.ajax(settings).done(function (response) {
		console.log(response);
		alert("질문 삭제가 완료되었습니다.");
		window.location = '/questions.html'
		
	}).fail(function(response){
		console.log(response.status)
		if(response.status === 500){
		  alert('질문을 삭제할 권한이 없습니다.')
		}else{
		  alert('서버에 문제가 발생하였습니다.')
		}
	  });
	  }


//답변 삭제
function deleteAnswer(answerId) {
	var settings = {
		"url": "http://dewdew.shop:8080/api/answers/"+answerId,
		"method": "DELETE",
		"timeout": 0,
		"headers": {
			"Authorization": localStorage.getItem('accessToken'),
		},
	};

	$.ajax(settings).done(function (response) {
		console.log(response);
		alert("답변 삭제가 완료되었습니다.")
		window.location = '/questionDetail.html'
	}).fail(function(response){
		console.log(response.status)
		if(response.status === 500){
		  alert('답변을 삭제할 권한이 없습니다.')
		}else{
		  alert('서버에 문제가 발생하였습니다.')
		}
	  });
	  }

//댓글 삭제
function deleteComment(answerId, commentId) {
	var settings = {
		"url": "http://dewdew.shop:8080/api/questions/answers/"+answerId+"/comments/"+commentId,
		"method": "DELETE",
		"timeout": 0,
		"headers": {
			"Authorization": localStorage.getItem('accessToken'),
		},
	  };
	  
	  $.ajax(settings).done(function (response) {
		console.log(response);
		alert("댓글 삭제가 완료되었습니다.");
		window.location = '/questionDetail.html'
	  }).fail(function(response){
		console.log(response.status)
		if(response.status === 500){
		  alert('댓글을 삭제할 권한이 없습니다.')
		}else{
		  alert('서버에 문제가 발생하였습니다.')
		}
	  });
	  }


//답변 좋아요
function likeAnswer(answerId) {
	// var likeButton = $(`#`+answerId+'-like-button');
	var settings = {
		"url": "http://dewdew.shop:8080/api/likes/answer/"+answerId,
		"method": "POST",
		"timeout": 0,
		"headers": {
			"Authorization": localStorage.getItem('accessToken'),
		},
	  };
	  
	  $.ajax(settings).done(function (response) {
		console.log(response);
		localStorage.setItem('answerId', response.answerId);//DB같은 역할
		window.location.reload();
		// var likes = parseInt(likeButton.text().match(/\d+/)[0]); // 좋아요 개수 가져오기
		// if (likeButton.hasClass('liked')) { // 이미 좋아요를 누른 경우
		// likeButton.removeClass('liked');
		// likeButton.html(`<span class="fa fa-heart-o" style="font-size: 16px; font-weight: 650;"></span> ${response.likes-1}`);
		// } else { // 좋아요를 누르지 않은 경우
		// likeButton.addClass('liked');
		// likeButton.html(`<span class="fa fa-heart" style="font-size: 16px; font-weight: 650;"></span> ${response.likes+1}`);
		// }
  })	.fail(function(response){
	console.log(response.status)
	if(response.status === 500){
	  alert('본인 글에 "좋아요"를 누를 수 없습니다.')
	  window.location.reload();
	}else{
	  alert('서버에 문제가 발생하였습니다.')
	}
  });
  
}
// function getLikeStatus(answerId) {
// 	var likeButton = $(`#`+answerId+'-like-button');
// 	var settings = {
// 	  "url": "http://dewdew.shop:8080/api/likes/answer/"+answerId,
// 	  "method": "GET",
// 	  "timeout": 0,
// 	  "headers": {
// 		"Authorization": localStorage.getItem('accessToken'),
// 	  },
// 	};
	
// 	$.ajax(settings).done(function (response) {
// 	  console.log(response);
// 	  if (response.isLiked) { // 좋아요를 누른 경우
// 		likeButton.addClass('liked');
// 		likeButton.html(`<span class="fa fa-heart" style="font-size: 16px; font-weight: 650;"></span> ${response.likes}`);
// 	  } else { // 좋아요를 누르지 않은 경우
// 		likeButton.removeClass('liked');
// 		likeButton.html(`<span class="fa fa-heart-o" style="font-size: 16px; font-weight: 650;"></span> ${response.likes}`);
// 	  }
// 	});
//   }
  
//   $(document).ready(function() {
// 	// 각 답변의 좋아요 상태를 가져와서 표시
// 	$('.comment').each(function() {
// 	  var answerId = $(this).attr('id');
// 	  getLikeStatus(answerId);
// 	});
//   });



//답변 채택
function answerIsSelected(answerId) {
	var settings = {
		"url": "http://dewdew.shop:8080/questions/"+localStorage.getItem('currentQuestion')+"/"+answerId,
		"method": "PATCH",
		"timeout": 0,
		"headers": {
			"Authorization": localStorage.getItem('accessToken'),
		},
	  };
	  
	  $.ajax(settings).done(function (response) {
		console.log(response);
		alert("답변 채택이 완료되었습니다.");
		window.location = '/questionDetail.html'
		
	  })	.fail(function(response){
		console.log(response.status)
		if(response.status === 500){
		  alert('채택을 할 권한이 없습니다.')
		  window.location.reload();
		}else{
		  alert('서버에 문제가 발생하였습니다.')
		}
	  });
}



function addAnswerIsSelectedHTML(answerId, nickname, content, createdAt, likes) {
	let tempHtml = makeAnswerIsSelected(answerId, nickname, content, createdAt, likes);
	return tempHtml
}



function makeAnswerIsSelected(answerId, nickname, content, createdAt, likes) {
	return `<div id="questionIsSelected" class="mt-5">
				<h5 class="checkAnswer"><i class="fa fa-check-circle-o" aria-hidden="true" style="color:#deb887; font-weight: bold;"> 질문자 채택 답변</i></h5>
				<div class="usermeta" id="username">
				<a>${nickname}</a>
				<div class="meta">${createdAt}
				<div class="w3-dropdown-hover w3-right">
					<div class="w3-dropdown-content w3-bar-block w3-border" style="right:0">
					</div>
				</div>
				</div>
				</div>
				<div class="questionContent">
				
				<p>${content}</p>
				</div>
				<div class="likeReply">
				<button class="nav-icon-btn like" type="button" style="border: none; color: #deb887;" ><span class="fa fa-heart-o" style="font-size: 16px; font-weight: 650;"></span> ${likes}</button>
				</div>
			</div>`;
}


//답변 수정 -> 수정 버튼을 누르면 기존 작성 내용을 수정 textarea에 넘겨줌
function editAnswer(answerId) {
	showAnswerEdits(answerId);
	let contents = $('#'+answerId+'-answerContent').text().trim();
	$('#'+answerId+'-answertextarea').val(contents);

}

function showAnswerEdits(answerId) {
	$('#'+answerId+'-answertextarea').show();
	$('#'+answerId+'-submit').show();

	$('#'+answerId+'-answerContent').show();
	$('#'+answerId+'-edit').hide();
	$('#'+answerId+'-delete').hide();
}

//답변 내용 수정
function submitEditAnswer(answerId) {
	var settings = {
		"url": "http://dewdew.shop:8080/api/answers/"+answerId,
		"method": "PUT",
		"timeout": 0,
		"headers": {
			"Authorization": localStorage.getItem('accessToken'),
		  	"Content-Type": "application/json"
		},
		"data": JSON.stringify({
		  "content": $('#'+answerId+'-answertextarea').val()
		}),
	  };
	  
	  $.ajax(settings).done(function (response) {
		alert("답변 수정이 완료되었습니다.")
		window.location = '/questionDetail.html'
		}).fail(function(response){
		console.log(response.status)
		if(response.status === 500){
		  alert('답변을 수정할 권한이 없습니다.')
		  window.location.reload();
		}else{
		  alert('서버에 문제가 발생하였습니다.')
		}
	  });
}




//댓글 수정 -> 수정 버튼을 누르면 기존 작성 내용을 수정 textarea에 넘겨줌
function editComment(answerId, commentId) {
	showCommentEdits(answerId, commentId);
	let contents = $('#'+answerId+'-comment').text().trim();
	$('#'+commentId+'-commenttextarea').val(contents);
}

function showCommentEdits(answerId, commentId) {
	$('#'+commentId+'-commenttextarea').show();
	$('#'+commentId+'-commentSubmit').show();

	$('#'+answerId+'-comment').show();

	$('#'+commentId+'-commentEdit').hide();
	$('#'+commentId+'-commentDelete').hide();
}


//댓글 내용 수정
function submitEditComment(answerId, commentId) {
	var settings = {
		"url": "http://dewdew.shop:8080/api/questions/answers/"+answerId+"/comments/"+commentId,
		"method": "PUT",
		"timeout": 0,
		"headers": {
			"Authorization": localStorage.getItem('accessToken'),
		  	"Content-Type": "application/json"
		},
		"data": JSON.stringify({
		  "content": $('#'+commentId+'-commenttextarea').val()
		}),
	  };
	  
	  $.ajax(settings).done(function (response) {
		alert("댓글 수정이 완료되었습니다.")
		window.location = '/questionDetail.html'
		})
		.fail(function(response){
		console.log(response.status)
		if(response.status === 500){
		  alert('댓글을 수정할 권한이 없습니다.')
		  window.location.reload();
		}else{
		  alert('서버에 문제가 발생하였습니다.')
		}
	  });
}