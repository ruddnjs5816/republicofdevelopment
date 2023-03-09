
(function ($) {

	getAllProducts;

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


//상품 등록
function uploadProduct() {
    var settings = {
        "url": "http://dewdew.shop:80/api/shop",
        "method": "POST",
        "timeout": 0,
        "headers": {
		    "Authorization": localStorage.getItem('accessToken'),
            "Content-Type": "application/json"
        },
        "data": JSON.stringify({
          "productName": $('#productName').val(),
          "price": $('#productPrice').val(),
          "productDescription": $('#productDetail').val()
        }),
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        alert('상품 등록이 완료되었습니다.')
      });
}


//상품 주문
function orderProduct(productId) {
    var settings = {
        "url": "http://dewdew.shop:80/shop/orders/"+productId,
        "method": "POST",
        "timeout": 0,
        "headers": {
		    "Authorization": localStorage.getItem('accessToken'),
        },
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        alert("주문이 완료되었습니다.")
        window.location.reload();

      });
}



function getAllProducts(page, size) {
    var settings = {
        "url": "http://dewdew.shop:80/api/shop?page=" + page + "&size=" + size,
        "method": "GET",
        "timeout": 0,
        "headers": {
		    "Authorization": localStorage.getItem('accessToken'),
        },
      };
      
      $.ajax(settings).done(function (response) {
        console.log(response);
        // $('#productCards').empty();

        for (let i = 0; i < response.length; i++) {
            let productList = response[i];
            if(i=== 0) {
                let tempHtml = addProductHTML0(productList.productId, productList.productName, productList.price, productList.productDescription);
                $('#productCards').append(tempHtml);
            }
            else if (i === 1) {
                let tempHtml = addProductHTML1(productList.productId, productList.productName, productList.price, productList.productDescription);
                $('#productCards').append(tempHtml);
            }
            else if (i === 2) {
                let tempHtml = addProductHTML2(productList.productId, productList.productName, productList.price, productList.productDescription);
                $('#productCards').append(tempHtml);
            }
            else if (i === 3) {
                let tempHtml = addProductHTML3(productList.productId, productList.productName, productList.price, productList.productDescription);
                $('#productCards').append(tempHtml);
            }
            else if (i === 4) {
                let tempHtml = addProductHTML4(productList.productId, productList.productName, productList.price, productList.productDescription);
                $('#productCards').append(tempHtml);
            }else if (i === 5) {
                let tempHtml = addProductHTML5(productList.productId, productList.productName, productList.price, productList.productDescription);
                $('#productCards').append(tempHtml);
            }else if (i === 6) {
                let tempHtml = addProductHTML6(productList.productId, productList.productName, productList.price, productList.productDescription);
                $('#productCards').append(tempHtml);
            }else if (i === 7) {
                let tempHtml = addProductHTML7(productList.productId, productList.productName, productList.price, productList.productDescription);
                $('#productCards').append(tempHtml);
            }

        }
        









        // // 페이지 버튼 업데이트
        // resetPageButtons();
        // createPageButtons(response.totalPages);
        // $('.page-btn[data-page="' + currentPage + '"]').addClass('active');
    });
}

// 초기 페이지 로드 시 첫 번째 페이지의 질문 목록 불러오기
getAllProducts(1, 8);

function addProductHTML0(productId, productName, price, productDescription) {
    let tempHtml = makeProductCard0(productId, productName, price, productDescription);
    console.log('test3')
    return tempHtml;
}

function makeProductCard0(productId, productName, price, productDescription) {
    return `<div class="col-md-6 col-lg-3">
                <div class="staff">
                    <div class="img-wrap d-flex align-items-stretch">
                        <div class="img align-self-stretch" style="background-image: url(../images/starbucks_1.jpeg);">
                        </div>
                    </div>
                    <div class="text pt-3 px-3 pb-4 text-center">
                        <h3 id="productName">${productName}</h3>
                        <span class="position mb-2" id="productPrice">${price} POINT</span>
                        <div class="faded">
                            <p id="productDetail">${productDescription}</p>
                            <ul class="ftco-social text-center">
                                <li class="" style="float: right;">
                                    <a href="#" onclick="orderProduct(${productId})"
                                        class="d-flex align-items-center justify-content-center" style="font-size: 11px; font-weight:600">BUY
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
             </div>`;
    }

function addProductHTML1(productId, productName, price, productDescription) {
    let tempHtml = makeProductCard1(productId, productName, price, productDescription);
    console.log('test3')
    return tempHtml;
}

function makeProductCard1(productId, productName, price, productDescription) {
    return `<div class="col-md-6 col-lg-3">
                <div class="staff">
                    <div class="img-wrap d-flex align-items-stretch">
                        <div class="img align-self-stretch" style="background-image: url(../images/베라.jpeg);">
                        </div>
                    </div>
                    <div class="text pt-3 px-3 pb-4 text-center">
                        <h3 id="productName">${productName}</h3>
                        <span class="position mb-2" id="productPrice">${price} POINT</span>
                        <div class="faded">
                            <p id="productDetail">${productDescription}</p>
                            <ul class="ftco-social text-center">
                                <li class="" style="float: right;">
                                    <a href="#" onclick="orderProduct(${productId})"
                                        class="d-flex align-items-center justify-content-center" style="font-size: 11px; font-weight:600">BUY
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                </div>`;
    }

function addProductHTML2(productId, productName, price, productDescription) {
    let tempHtml = makeProductCard2(productId, productName, price, productDescription);
    console.log('test3')
    return tempHtml;
}

function makeProductCard2(productId, productName, price, productDescription) {
    return `<div class="col-md-6 col-lg-3">
                <div class="staff">
                    <div class="img-wrap d-flex align-items-stretch">
                        <div class="img align-self-stretch" style="background-image: url(../images/starbucks_2.jpeg);">
                        </div>
                    </div>
                    <div class="text pt-3 px-3 pb-4 text-center">
                        <h3 id="productName">${productName}</h3>
                        <span class="position mb-2" id="productPrice">${price} POINT</span>
                        <div class="faded">
                            <p id="productDetail">${productDescription}</p>
                            <ul class="ftco-social text-center">
                                <li class="" style="float: right;">
                                    <a href="#" onclick="orderProduct(${productId})"
                                        class="d-flex align-items-center justify-content-center" style="font-size: 11px; font-weight:600">BUY
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                </div>`;
    }

function addProductHTML3(productId, productName, price, productDescription) {
    let tempHtml = makeProductCard3(productId, productName, price, productDescription);
    console.log('test3')
    return tempHtml;
}

function makeProductCard3(productId, productName, price, productDescription) {
    return `<div class="col-md-6 col-lg-3">
                <div class="staff">
                    <div class="img-wrap d-flex align-items-stretch">
                        <div class="img align-self-stretch" style="background-image: url(../images/치킨.jpeg);">
                        </div>
                    </div>
                    <div class="text pt-3 px-3 pb-4 text-center">
                        <h3 id="productName">${productName}</h3>
                        <span class="position mb-2" id="productPrice">${price} POINT</span>
                        <div class="faded">
                            <p id="productDetail">${productDescription}</p>
                            <ul class="ftco-social text-center">
                                <li class="" style="float: right;">
                                    <a href="#" onclick="orderProduct(${productId})"
                                        class="d-flex align-items-center justify-content-center" style="font-size: 11px; font-weight:600">BUY
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                </div>`;
    }

function addProductHTML4(productId, productName, price, productDescription) {
    let tempHtml = makeProductCard4(productId, productName, price, productDescription);
    console.log('test3')
    return tempHtml;
}

function makeProductCard4(productId, productName, price, productDescription) {
    return `<div class="col-md-6 col-lg-3">
                <div class="staff">
                    <div class="img-wrap d-flex align-items-stretch">
                        <div class="img align-self-stretch" style="background-image: url(../images/투썸케이크.jpeg);">
                        </div>
                    </div>
                    <div class="text pt-3 px-3 pb-4 text-center">
                        <h3 id="productName">${productName}</h3>
                        <span class="position mb-2" id="productPrice">${price} POINT</span>
                        <div class="faded">
                            <p id="productDetail">${productDescription}</p>
                            <ul class="ftco-social text-center">
                                <li class="" style="float: right;">
                                    <a href="#" onclick="orderProduct(${productId})"
                                        class="d-flex align-items-center justify-content-center" style="font-size: 11px; font-weight:600">BUY
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                </div>`;
    }

function addProductHTML5(productId, productName, price, productDescription) {
    let tempHtml = makeProductCard5(productId, productName, price, productDescription);
    console.log('test3')
    return tempHtml;
}

function makeProductCard5(productId, productName, price, productDescription) {
    return `<div class="col-md-6 col-lg-3">
                <div class="staff">
                    <div class="img-wrap d-flex align-items-stretch">
                        <div class="img align-self-stretch" style="background-image: url(../images/스벅3.jpeg);">
                        </div>
                    </div>
                    <div class="text pt-3 px-3 pb-4 text-center">
                        <h3 id="productName">${productName}</h3>
                        <span class="position mb-2" id="productPrice">${price} POINT</span>
                        <div class="faded">
                            <p id="productDetail">${productDescription}</p>
                            <ul class="ftco-social text-center">
                                <li class="" style="float: right;">
                                    <a href="#" onclick="orderProduct(${productId})"
                                        class="d-flex align-items-center justify-content-center" style="font-size: 11px; font-weight:600">BUY
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                </div>`;
    }

function addProductHTML6(productId, productName, price, productDescription) {
    let tempHtml = makeProductCard6(productId, productName, price, productDescription);
    console.log('test3')
    return tempHtml;
}

function makeProductCard6(productId, productName, price, productDescription) {
    return `<div class="col-md-6 col-lg-3">
                <div class="staff">
                    <div class="img-wrap d-flex align-items-stretch">
                        <div class="img align-self-stretch" style="background-image: url(../images/도넛.jpeg);">
                        </div>
                    </div>
                    <div class="text pt-3 px-3 pb-4 text-center">
                        <h3 id="productName">${productName}</h3>
                        <span class="position mb-2" id="productPrice">${price} POINT</span>
                        <div class="faded">
                            <p id="productDetail">${productDescription}</p>
                            <ul class="ftco-social text-center">
                                <li class="" style="float: right;">
                                    <a href="#" onclick="orderProduct(${productId})"
                                        class="d-flex align-items-center justify-content-center" style="font-size: 11px; font-weight:600">BUY
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                </div>`;
    }

    function addProductHTML7(productId, productName, price, productDescription) {
        let tempHtml = makeProductCard7(productId, productName, price, productDescription);
        console.log('test3')
        return tempHtml;
    }
    
    function makeProductCard7(productId, productName, price, productDescription) {
        return `<div class="col-md-6 col-lg-3">
                    <div class="staff">
                        <div class="img-wrap d-flex align-items-stretch">
                            <div class="img align-self-stretch" style="background-image: url(../images/hongsam_1.jpeg);">
                            </div>
                        </div>
                        <div class="text pt-3 px-3 pb-4 text-center">
                            <h3 id="productName">${productName}</h3>
                            <span class="position mb-2" id="productPrice">${price} POINT</span>
                            <div class="faded">
                                <p id="productDetail">${productDescription}</p>
                                <ul class="ftco-social text-center">
                                    <li class="" style="float: right;">
                                        <a href="#" onclick="orderProduct(${productId})"
                                            class="d-flex align-items-center justify-content-center" style="font-size: 11px; font-weight:600">BUY
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    </div>`;
        }

    