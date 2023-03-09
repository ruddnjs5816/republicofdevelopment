(function ($) {
    getUserMe();
    getMyInfo();
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();
    
    
    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    // Sidebar Toggler
    $('.sidebar-toggler').click(function () {
        $('.sidebar, .content').toggleClass("open");
        return false;
    });


    // Progress Bar
    $('.pg-bar').waypoint(function () {
        $('.progress .progress-bar').each(function () {
            $(this).css("width", $(this).attr("aria-valuenow") + '%');
        });
    }, {offset: '80%'});


    // Calender
    $('#calender').datetimepicker({
        inline: true,
        format: 'L'
    });


    // Testimonials carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        items: 1,
        dots: true,
        loop: true,
        nav : false
    });


    // Worldwide Sales Chart
    var ctx1 = $("#worldwide-sales").get(0).getContext("2d");
    var myChart1 = new Chart(ctx1, {
        type: "bar",
        data: {
            labels: ["2016", "2017", "2018", "2019", "2020", "2021", "2022"],
            datasets: [{
                    label: "USA",
                    data: [15, 30, 55, 65, 60, 80, 95],
                    backgroundColor: "rgba(0, 156, 255, .7)"
                },
                {
                    label: "UK",
                    data: [8, 35, 40, 60, 70, 55, 75],
                    backgroundColor: "rgba(0, 156, 255, .5)"
                },
                {
                    label: "AU",
                    data: [12, 25, 45, 55, 65, 70, 60],
                    backgroundColor: "rgba(0, 156, 255, .3)"
                }
            ]
            },
        options: {
            responsive: true
        }
    });


    // Salse & Revenue Chart
    var ctx2 = $("#salse-revenue").get(0).getContext("2d");
    var myChart2 = new Chart(ctx2, {
        type: "line",
        data: {
            labels: ["2016", "2017", "2018", "2019", "2020", "2021", "2022"],
            datasets: [{
                    label: "Salse",
                    data: [15, 30, 55, 45, 70, 65, 85],
                    backgroundColor: "rgba(0, 156, 255, .5)",
                    fill: true
                },
                {
                    label: "Revenue",
                    data: [99, 135, 170, 130, 190, 180, 270],
                    backgroundColor: "rgba(0, 156, 255, .3)",
                    fill: true
                }
            ]
            },
        options: {
            responsive: true
        }
    });
    


    // Single Line Chart
    var ctx3 = $("#line-chart").get(0).getContext("2d");
    var myChart3 = new Chart(ctx3, {
        type: "line",
        data: {
            labels: [50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150],
            datasets: [{
                label: "Salse",
                fill: false,
                backgroundColor: "rgba(0, 156, 255, .3)",
                data: [7, 8, 8, 9, 9, 9, 10, 11, 14, 14, 15]
            }]
        },
        options: {
            responsive: true
        }
    });


    // Single Bar Chart
    var ctx4 = $("#bar-chart").get(0).getContext("2d");
    var myChart4 = new Chart(ctx4, {
        type: "bar",
        data: {
            labels: ["Italy", "France", "Spain", "USA", "Argentina"],
            datasets: [{
                backgroundColor: [
                    "rgba(0, 156, 255, .7)",
                    "rgba(0, 156, 255, .6)",
                    "rgba(0, 156, 255, .5)",
                    "rgba(0, 156, 255, .4)",
                    "rgba(0, 156, 255, .3)"
                ],
                data: [55, 49, 44, 24, 15]
            }]
        },
        options: {
            responsive: true
        }
    });


    // Pie Chart
    var ctx5 = $("#pie-chart").get(0).getContext("2d");
    var myChart5 = new Chart(ctx5, {
        type: "pie",
        data: {
            labels: ["Italy", "France", "Spain", "USA", "Argentina"],
            datasets: [{
                backgroundColor: [
                    "rgba(0, 156, 255, .7)",
                    "rgba(0, 156, 255, .6)",
                    "rgba(0, 156, 255, .5)",
                    "rgba(0, 156, 255, .4)",
                    "rgba(0, 156, 255, .3)"
                ],
                data: [55, 49, 44, 24, 15]
            }]
        },
        options: {
            responsive: true
        }
    });


    // Doughnut Chart
    var ctx6 = $("#doughnut-chart").get(0).getContext("2d");
    var myChart6 = new Chart(ctx6, {
        type: "doughnut",
        data: {
            labels: ["Italy", "France", "Spain", "USA", "Argentina"],
            datasets: [{
                backgroundColor: [
                    "rgba(0, 156, 255, .7)",
                    "rgba(0, 156, 255, .6)",
                    "rgba(0, 156, 255, .5)",
                    "rgba(0, 156, 255, .4)",
                    "rgba(0, 156, 255, .3)"
                ],
                data: [55, 49, 44, 24, 15]
            }]
        },
        options: {
            responsive: true
        }
    });

    
})(jQuery);



function getUserMe(){
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
		$('#loginUser').empty();
		$('#loginUser').append(response.username + '님')
        $('#grade').append(response.grade)
	  });
}




//-------------------------------------------------------------------------
function getMyInfo(){
    var settings = {
        "url": "http://dewdew.shop:8080/users/mypage",
        "method": "GET",
        "timeout": 0,
        "headers": {
          "Authorization": localStorage.getItem('accessToken')
        },
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        console.log(response.nickname);

        let profileList = response;
        let tempHtml = addAnswerHTML(profileList);
        $('#profiles').append(tempHtml);
      
    
      });
    }

    function addAnswerHTML(profileList) {
		let tempHtml = makeProfile(profileList);
		$('#profiles').append(tempHtml);
	  }

	function makeProfile(profileList){
	return`
    <tr>
    <th scope="row">아이디</th>
    <td><span>${profileList.username}</span></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>
<tr>
    <th scope="row">닉네임</th>
    <td><span id="user_nickname">${profileList.nickname}</span></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>
<tr>
    <th scope="row">깃 허브 주소</th>
    <td><span>${profileList.githubAddress}</span></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>
<tr>
    <th scope="row">핸드폰 번호</th>
    <td><span >${profileList.phoneNumber}</span></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>
<tr>
    <th scope="row">레이팅</th>
    <td><span>${profileList.rating}</span></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>
<tr>
    <th scope="row">등급</th>
    <td><span>${profileList.grade}</span></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>
<tr>
    <th scope="row">포인트</th>
    <td><span>${profileList.point}</span></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>
<tr>
    <th scope="row">자기소개</th>
    <td><span>${profileList.introduce}</span></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
    <td></td>
</tr>`
}


//--------------------------------------------------------------
// 수정 눌렀을때 실행 
function editProfile(){
    var settings = {
        "url": "http://dewdew.shop:8080/users/mypage",
        "method": "GET",
        "timeout": 0,
        "headers": {
          "Authorization": localStorage.getItem('accessToken')
        },
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        $('#profiles').empty();
        
        let editprofileList = response;
        let tempHtml = addEditProfileHTML(editprofileList);
        $('#profiles').append(tempHtml);
      });
}

function addEditProfileHTML(editprofileList) {
    let tempHtml = makeEditProfile(editprofileList);
    $('#profiles').append(tempHtml);
  }


function makeEditProfile(editprofileList){
return`
<tr>
<th scope="row">아이디</th>
<td><span>${editprofileList.username}</span></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>

<tr>
// <th scope="row">닉네임</th>
// <td><span>${editprofileList.nickname}<input class="form-control" type="text" id="editNickname" placeholder="수정할 닉네임을 입력해주세요"></span></td>
// <td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>

<tr>
<th scope="row">깃 허브 주소</th>
<td><span>${editprofileList.githubAddress}<input class="form-control" type="text" id="editGithubAddress" placeholder="수정할 깃허브 주소를 입력해주세요" ></span></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>

<tr>
<th scope="row">핸드폰 번호</th>
<td><span>${editprofileList.phoneNumber}<input class="form-control" type="text" id="editPhoneNumber" placeholder="수정할 번호를 입력해주세요" ></span></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>

<th scope="row">레이팅</th>
<td><span>${editprofileList.rating}</span></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>

<tr>
<th scope="row">등급</th>
<td><span>${editprofileList.grade}</span></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr>
<th scope="row">포인트</th>
<td><span>${editprofileList.point}</span></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>

<tr>
<th scope="row">자기소개</th>
<td><span>${editprofileList.introduce}<textarea class="form-control" type="text" id="editIntroduce" placeholder="수정할 자기소개 내용을 입력해주세요" ></textarea></span></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>`
}





//---------------------------------------------------------------------------------------------
// 완료 눌렀을때 실행 
function editSuccess(){
    
    
    var settings = {
        "url": "http://dewdew.shop:8080/users/mypage",
        "method": "PATCH",
        "timeout": 0,
        "headers": {
          "Authorization": localStorage.getItem('accessToken'),
          "Content-Type": "application/json"
        },
        "data": JSON.stringify({
          "nickname": $('#editNickname').val(),
          "phoneNumber": $('#editPhoneNumber').val(),
          "githubAddress": $('#editGithubAddress').val(),
          "introduce": $('#editIntroduce').val()
        }),
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        // $('#profiles').empty();

        // let editprofileList = response;
        // let tempHtml = addEditProfileHTML(editprofileList);
        // $('#profiles').append(tempHtml);

        

      });

      window.location.reload();
}

// function addEditProfileHTML(editprofileList) {
   
    
//     let tempHtml = makeEditProfile1(editprofileList);
//     $('#profiles').append(tempHtml);
//   }

// function makeEditProfile1(editprofileList){

//  return`
// <tr>
// <th scope="row">아이디</th>
// <td><span>${editprofileList.username}</span></td>
// <input class="form-control" type="text" placeholder="Default input" >
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// </tr>

// <tr>
// <th scope="row">닉네임</th>
// <td><span"><input class="form-control" type="text" id="editNickname" ></span></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// </tr>

// <tr>
// <th scope="row">깃 허브 주소</th>
// <td><span"><input class="form-control" type="text" id="editGithubAddress" ></span></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// </tr>

// <tr>
// <th scope="row">핸드폰 번호</th>
// <td><span"><input class="form-control" type="text" id="editPhoneNumber" placeholder=''></textarea></span></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// </tr>
// <tr>

// <th scope="row">레이팅</th>
// <td><span>${editprofileList.rating}</span></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// </tr>

// <tr>
// <th scope="row">등급</th>
// <td><span>${editprofileList.grade}</span></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// </tr>

// <tr>
// <th scope="row">포인트</th>
// <td><span>${editprofileList.point}</span></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// </tr>

// <tr>
// <th scope="row">자기소개</th>
// <td><span"><textarea class="form-control" type="text" id="editIntroduce"></textarea></span></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// <td></td>
// </tr>`
// }


   
// function editAnswer(answerId) {
// 	showAnswerEdits(answerId);
	

// }
