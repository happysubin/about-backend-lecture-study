# ControllerV3 도입

* 뷰 경로와 뷰에 보여줄 값은 ModelView에 담아서 리턴한다.
* 뷰 리졸버가 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경한다. 그리고 실제 물리 경로가 있는 MyView 객체를 반환 한다.
* 뷰 객체의 `render()` 는 모델 정보도 함께 받는다.
* request.getAttribute()` 로 데이터를 조회하기 때문에, 모델의 데이터를 꺼내서`request.setAttribute()` 로 담아둔다