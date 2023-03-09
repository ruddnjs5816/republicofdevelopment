(function ($) {
    getUserMe();
    getMyOrderList();
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

		"url": "http://localhost:8080/users/mypage",
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


//주문 취소
function cancelOrder(orderId) {
    var settings = {
        "url": "http://localhost:8080/shop/orders/cancel/"+orderId,
        "method": "POST",
        "timeout": 0,
        "headers": {
		    "Authorization": localStorage.getItem('accessToken'),
        },
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        alert("주문이 취소되었습니다.")
        // window.location.reload();

        $('#'+orderId+'-cancelButton').hide();
        $('#'+orderId+'-cancelOrder').show();
      });
}






//-------------------------------------------------------------------------
function getMyOrderList(){
    var settings = {
        "url": "http://localhost:8080/shop/orderList",
        "method": "GET",
        "timeout": 0,
        "headers": {
          "Authorization": 
          localStorage.getItem('accessToken')
        },
      };
    
      $('#orderList').empty();

      $.ajax(settings).done(function (response) {
        console.log(response);
        console.log(response[0].orderStatus)
        for(let i=0; i<response.length; i++){
            
            let orderDto = response[i];
            let tempHtml = addOrderListHTML(orderDto);
    
            $('#orderList').append(tempHtml);

            if(orderDto.orderStatus === "CANCEL") {
                $('#'+orderDto.orderId+'-cancelButton').hide();
                $('#'+orderDto.orderId+'-cancelOrder').show();
            }
            else if (orderDto.orderStatus === "ORDER") {
                $('#'+orderDto.orderId+'-cancelButton').show();
                $('#'+orderDto.orderId+'-cancelOrder').hide();
            }
        }


        
      });


      function addOrderListHTML(orderDto) {
        let tempHtml = makeOrderList(orderDto);
        $('#orderList').append(tempHtml);
      }
    
    // function makeOrderList(orderDto){
       
    //     return `
    //     <li class="list-group-item d-flex justify-content-between align-items-center">
    //         <span>${orderDto.productName}</span>
    //         <span>
    //             <span class="badge bg-secondary">가격 ${orderDto.price} </span>
    //             <span class="badge bg-dark">주문 날짜 ${orderDto.createdAt} </span>
    //             <tr>
    //                 <td>
    //                     <button style="
    //                         background-color: #d4a74d; 
    //                         border: none;
    //                         color: white;
    //                         padding: 8px 10px;
    //                         text-align: center;
    //                         text-decoration: none;
    //                         display: inline-block;
    //                         font-size: 10px;
    //                         margin: 4px 2px;
    //                         cursor: pointer;
    //                         border-radius: 10px;
    //                     ">
    //                         주문 취소
    //                     </button>
    //                 </td>
    //             </tr>
    //         </span>
    //     </li>
    // `;
    // }

    function makeOrderList(orderDto){
        return `
            <li class="list-group-item d-flex justify-content-between align-items-center" style="white-space: nowrap;">
                <span>${orderDto.productName}</span>
                <span>
                    <span class="badge bg-secondary">주문 가격 : ${orderDto.price} POINT</span>
                    <span class="badge bg-dark">주문 날짜 ${orderDto.createdAt} </span>
                    <tr>
                        <td>
                            <button id="${orderDto.orderId}-cancelButton" 
                                style="
                                background-color: burlywood; 
                                border: none;
                                color: white;
                                padding: 8px 10px;
                                text-align: center;
                                text-decoration: none;
                                display: inline-block;
                                font-size: 10px;
                                margin: 4px 2px;
                                cursor: pointer;
                                border-radius: 10px;" 
                                onclick="cancelOrder(${orderDto.orderId})">
                                주문 취소
                            </button>
                            <a id="${orderDto.orderId}-cancelOrder"
                                style="
                                background-color: rgb(144 30 0); 
                                border: none;
                                color: white;
                                padding: 8px 10px;
                                text-align: center;
                                text-decoration: none;
                                display: inline-block;
                                font-size: 10px;
                                margin: 4px 2px;
                                cursor: pointer;
                                border-radius: 10px;
                                display: none;" >
                                취소된 주문입니다.
                            </a>
                        </td>
                    </tr>
                </span>
            </li>
        `;
    }
    

}

//-------------------------------------------------

