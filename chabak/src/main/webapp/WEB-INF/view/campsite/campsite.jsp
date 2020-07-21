<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>슬기로운 차박생활</title>
    <link href="/resources/css/index.css" rel="stylesheet">
    <link href="/resources/css/campsite.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script type="text/javascript" src=" http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript" src="/resources/js/address_select.js"></script>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
</head>
<body>
<!-- 헤더(인클루드 적용) -->
<div id="header">
    <jsp:include page="/header"/>
</div>
<hr class="top_hr"><br>
<br>
<div class="container">

    <!-- 부제 -->
    <div class = "top">
        <h1>야영지 정보</h1>
    </div>
    <!-- 지역검색 -->
    <div class="campsite_section">

        <!-- 지역 선택-->
            <div class="address_select">
                <form onsubmit="searchPlaces(); return false;">
                    <select name="sido" id="sido"></select>
                    <select name="gugun" id="gugun"></select>
                    <div class="option">
                        <div>
                                <input type="text" value=${keyword} id="keyword" size="15">
                                <button type="submit">검색하기</button>
                        </div>
                    </div>
                </form>
            </div>

    <!-- 지도 생성 -->
    <!-- 카카오맵 -->
    <div class="map_wrap">
        <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>

        <div id="menu_wrap" class="bg_white">
            <hr>
            <ul id="placesList"></ul>
            <div id="pagination"></div>
        </div>
    </div>

    <hr class="top_hr">

    <!-- 커뮤니티리뷰 -->
    <div class="blog_aticle">
        <h2>커뮤니티 리뷰</h2>
        <ul>
            <li>
                <p class="best_id">ID:차박차박</p>
                <div class="best_img">
                    <img src="/resources/img/reviews/01.jpg">
                </div>
                </p>
                <p class="best_title">슬기로운 차박생활♥</p>
                <p class="best_content">이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며...
                    이날은 우리 하동여행 갔던날 설레고 긴장되는 첫 차박을 꿈꾸며
                </p>
                <p class="select_community"><a href="community.html">자세히보기</a></p>
            </li>
            <li><img><p>영역 나누기2</p></li>
            <li><img><p>영역 나누기3</p></li>
            <li><img><p>영역 나누기4</p></li>
            <li><img><p>영역 나누기5</p></li>
        </ul>
        <!-- 페이지 버튼 -->
        <div class="community_link">
            <button class='fas fa-angle-left'></button>
            <button class='fas fa-circle'></button>
            <button class='far fa-circle'></button>
            <button class='far fa-circle'></button>
            <button class='fas fa-angle-right'></button>
        </div>
    </div>

    <!--footer-->
    <div class="footer">
        <img src="/resources/img/footer/footer.png">
    </div>
</div>

<!-- 카카오 MAP -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9613dfdd64d6afc0ba0dc90bfcd01cf3&libraries=services"></script>
<script>
    // 마커를 담을 배열입니다
    var markers = [];

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

    // 지도를 생성합니다
    var map = new kakao.maps.Map(mapContainer, mapOption);

    // 장소 검색 객체를 생성합니다
    var ps = new kakao.maps.services.Places();

    // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
    var infowindow = new kakao.maps.InfoWindow({zIndex:1});

    // 키워드로 장소를 검색합니다
    searchPlaces();

    // 키워드 검색을 요청하는 함수입니다
    function searchPlaces() {

        var keyword = document.getElementById('keyword').value;
        var sido = document.getElementById('sido').value;
        var gugun = document.getElementById('gugun').value;

        if(keyword=='자동차'){
            if(sido=='시/도 선택'){
                sido=' ';
            }
            if(gugun=='구/군 선택'){
                gugun=' ';
            }
            keyword = sido+" "+gugun+" "+'자동차야영장';
        }
        else if(keyword=='차박'){
            if(sido=='시/도 선택'){
                sido=' ';
            }
            if(gugun=='구/군 선택'){
                gugun=' ';
            }
            keyword = sido+" "+gugun+" "+'자동차야영장';
        }
        else{
            if(sido=='시/도 선택'){
                sido=' ';
            }
            if(gugun=='구/군 선택'){
                gugun=' ';
            }
            keyword = sido+" "+gugun+" "+keyword;
        }

        if (!keyword.replace(/^\s+|\s+$/g, '')) {
            alert('키워드를 입력해주세요!');
            return false;
        }

        // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
        ps.keywordSearch( keyword, placesSearchCB);
    }

    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    function placesSearchCB(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {

            // 정상적으로 검색이 완료됐으면
            // 검색 목록과 마커를 표출합니다
            displayPlaces(data);

            // 페이지 번호를 표출합니다
            displayPagination(pagination);

        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

            alert('검색 결과가 존재하지 않습니다.');
            return;

        } else if (status === kakao.maps.services.Status.ERROR) {

            alert('검색 결과 중 오류가 발생했습니다.');
            return;

        }
    }

    // 검색 결과 목록과 마커를 표출하는 함수입니다
    function displayPlaces(places) {

        var listEl = document.getElementById('placesList'),
            menuEl = document.getElementById('menu_wrap'),
            fragment = document.createDocumentFragment(),
            bounds = new kakao.maps.LatLngBounds(),
            listStr = '';

        // 검색 결과 목록에 추가된 항목들을 제거합니다
        removeAllChildNods(listEl);

        // 지도에 표시되고 있는 마커를 제거합니다
        removeMarker();

        for ( var i=0; i<places.length; i++ ) {

            // 마커를 생성하고 지도에 표시합니다
            var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
                marker = addMarker(placePosition, i),
                itemEl = getListItem(i, places[i], placePosition); // 검색 결과 항목 Element를 생성합니다

            // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
            // LatLngBounds 객체에 좌표를 추가합니다
            bounds.extend(placePosition);

            // 마커와 검색결과 항목에 mouseover 했을때
            // 해당 장소에 인포윈도우에 장소명을 표시합니다
            // mouseout 했을 때는 인포윈도우를 닫습니다
            (function(marker, title) {
                kakao.maps.event.addListener(marker, 'mouseover', function() {
                    displayInfowindow(marker, title);
                });

                kakao.maps.event.addListener(marker, 'mouseout', function() {
                    infowindow.close();
                });

                itemEl.onmouseover =  function () {
                    displayInfowindow(marker, title);
                };

                itemEl.onmouseout =  function () {
                    infowindow.close();
                };
            })(marker, places[i].place_name);

            fragment.appendChild(itemEl);
        }

        // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
        listEl.appendChild(fragment);
        menuEl.scrollTop = 0;

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
    }

    // 검색결과 항목을 Element로 반환하는 함수입니다
    function getListItem(index, places, placePosition) {
        var el = document.createElement('li'),
            itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
                '<form id="campsitePlacseDetail"><div class="info" data-ga="' + placePosition.Ga +'" data-ha="' + placePosition.Ha + '" data-ta="' + places.place_name +'">' +

                // 클릭 이벤트로 선택된 야영지 위도,경도 데이터 보내는 이벤트
                "<h5><a href='#' onclick='selectPlaces(this.parentNode.parentNode)'>" +
                "<input type='hidden' name='latitude'><input type='hidden' name='longitude'><input type='hidden' name='plname'>"


                + places.place_name + '</a></h5>';

        if (places.road_address_name) {
            itemStr += '<span>' + places.road_address_name + '</span>' +
                '   <span class="jibun gray">' +  places.address_name  + '</span>';
        } else {
            itemStr += '    <span>' +  places.address_name  + '</span>';
        }

        itemStr += '  <span class="tel">' + places.phone  + '</span>' +
            '</form></div>';

        el.innerHTML = itemStr;
        el.className = 'item';

        return el;

    }

    // 장소 선택하고 난 후 위도,경도 가져오는 함수
          function selectPlaces(obj) {
              var lat = $(obj).data("ha"), //위도
                  long = $(obj).data("ga"),//경도
                  plse = $(obj).data("ta");

              var campsiteTest=document.getElementById("campsitePlacseDetail");

              campsiteTest.latitude.value = lat;
              campsiteTest.longitude.value = long;
              campsiteTest.plname.value = plse;
              campsiteTest.action="/campsite/campsitePlaceDetail";
              campsiteTest.method="post"; //POST방식
              campsiteTest.submit();

          }

    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    function addMarker(position, idx, title) {
        var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
            imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
            imgOptions =  {
                spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
                spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
                offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
            },
            markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
            marker = new kakao.maps.Marker({
                position: position, // 마커의 위치
                image: markerImage
            });

        marker.setMap(map); // 지도 위에 마커를 표출합니다
        markers.push(marker);  // 배열에 생성된 마커를 추가합니다

        return marker;
    }

    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    function removeMarker() {
        for ( var i = 0; i < markers.length; i++ ) {
            markers[i].setMap(null);
        }
        markers = [];
    }

    // 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
    function displayPagination(pagination) {
        var paginationEl = document.getElementById('pagination'),
            fragment = document.createDocumentFragment(),
            i;

        // 기존에 추가된 페이지번호를 삭제합니다
        while (paginationEl.hasChildNodes()) {
            paginationEl.removeChild (paginationEl.lastChild);
        }

        for (i=1; i<=pagination.last; i++) {
            var el = document.createElement('a');
            el.href = "#";
            el.innerHTML = i;

            if (i===pagination.current) {
                el.className = 'on';
            } else {
                el.onclick = (function(i) {
                    return function() {
                        pagination.gotoPage(i);
                    }
                })(i);
            }

            fragment.appendChild(el);
        }
        paginationEl.appendChild(fragment);
    }

    // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
    // 인포윈도우에 장소명을 표시합니다
    function displayInfowindow(marker, title) {
        var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

        infowindow.setContent(content);
        infowindow.open(map, marker);
    }

    // 검색결과 목록의 자식 Element를 제거하는 함수입니다
    function removeAllChildNods(el) {
        while (el.hasChildNodes()) {
            el.removeChild (el.lastChild);
        }
    }
</script>

</body>
</html>
