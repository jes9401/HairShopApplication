# HairShopApplication
3학년 1학기 모바일 프로그래밍 과목 프로젝트 **헤어의 참견**
### 참여 인원
- 팀장: 권영언(담당분야 : 총괄, S/W 제작, Simulation 및 성능개선) 
- 팀원: 정의석(S/W 제작, 회의록)
- 팀원: 권영인(자료조사, UI 제작)

# 헤어샵 플랫폼 어플

## 개발 환경
- 안드로이드 스튜디오(java)
- 카페24 PHP서버 호스팅
- mysql database(phpmyadmin)

## 화면 레이아웃 및 동작 설명
<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/login.png" style="width: 300px
    ; height: 500px;">
</div>

- 어플 실행 시 로그인 화면 출력


<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/register.png" style="width: 300px
    ; height: 500px;">
</div>

<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/designer.png" style="width: 300px
    ; height: 500px;">
</div>

- 회원가입 버튼을 선택하면 가입화면으로 이동

- 사용자면 아이디/비밀번호, 이름, 닉네임, 전화번호와 성별을 기입하여 회원가입이 되고, 디자이너면 디자이너 버튼을 선택하여 디자이너 세부사항 화면으로 넘어와 미용자격증 사진을 등록하여 승인을 받으면 회원가입이 됨.

<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/main.png" style="width: 300px
    ; height: 500px;">
</div>

- 로그인 후 메인화면(n달의 참견)으로 이동
- 매월 남녀 추천 헤어스타일이 업데이트 됨.

<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/question.png" style="width: 300px
    ; height: 500px;">
</div>

- 메인화면 오른쪽 상단에 물음표 버튼을 누르면 헤어의 참견 도움말을 볼 수 있다.

<div style="width: 600px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/style.png" style="width: 600px
    ; height: 500px;">
</div>

- 상단 바에서 스타일 버튼을 선택하면 스타일 화면으로 이동. 하단의 남자, 여자 버튼을
선택하여 각 아이콘 버튼을 누르면 스타일 사진들을 볼 수 있다.

<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/styledetail.png" style="width: 300px
    ; height: 500px;">
</div>

- 예시 : 스타일 화면에서 여자 버튼을 선택하고 펌 아이콘을 누르면 여자 펌 화면이 나온다.
  - (회원들이 후기 게시판에 올린 사진들로 구성)

<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/please.png" style="width: 300px
    ; height: 500px;">
</div>

- 상단 바에서 참견해주세요 버튼을 선택하면 참견해주세요 화면으로 이동.
- 참견해주세요 에서는 고민 글과 사진을 올리면 디자이너들이 정독 후 추천과 코멘트를 해준다.
- 게시글 수정 및 삭제가 가능하며 또한 비밀글로 설정을 해놓으면 자기 자신과 디자이너에게만 공개가 된다.

<div style="width: 600px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/pleasedetail.png" style="width: 600px
    ; height: 500px;">
</div>

- 참견해주세요 화면에서 글쓰기 버튼을 누르면 제목과 내용, 사진을 2장 까지 첨부할 수 있으며 공개여부를 설정할 수 있다.


<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/comment.png" style="width: 300px
    ; height: 500px;">
</div>

- 게시글을 올리면 서버 및 데이터베이스와의 통신을 통해 게시판에 글이 올라오고 게시글을 선택하면 게시된 글을 볼 수 있다.



<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/review.png" style="width: 300px
    ; height: 500px;">
</div>

- 상단 바에서 후기 버튼을 선택하면 후기 화면으로 이동
- 후기 화면에서는 사용자가 헤어샵 이용 후 좋았던 점이나 아쉬웠던 점들을 올린 게시글들을 평점과 함께 볼 수 있다

<div style="width: 600px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/pleasedetail.png" style="width: 600px
    ; height: 500px;">
</div>

- 후기 화면에서 글쓰기 버튼을 누르면 제목과 내용, 사진을 2장까지 첨부가 가능하며 평점을 매길 수 있다.


<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/reviewdetail.png" style="width: 300px
    ; height: 500px;">
</div>
- 게시글을 올리면 서버 및 데이터베이스와의 통신을 통해 게시판에 글이 올라오고 게시글을 선택하면 게시된 글과 사진을 볼 수 있다.

<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/mypage.png" style="width: 300px
    ; height: 500px;">
</div>

- 하단 바에서 마이페이지 버튼을 누르면 마이페이지 화면으로 이동한다.
- 예약할 디자이너 명을 적고 선택하면 디자이너의 헤어샵 번호가 출력되며 전화 또는 문자를 보내 예약이 가능하고, 하단에는 광고를 출력한다.
- 또한 상단에 로그아웃 버튼을 누르면 로그인 화면으로 이동한다.

<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/mypost.png" style="width: 300px
    ; height: 500px;">
</div>

- 마이페이지에서 내가 쓴 글 버튼을 누르면 사용자가 참견해주세요와 후기에 올린 게시글을 볼 수 있다.

<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/update.png" style="width: 300px
    ; height: 500px;">
</div>
- 마이페이지에서 회원 정보 수정 버튼을 누르면 아이디를 제외한 회원 정보를 수정할 수 있다.
- 이 때, 자신의 비밀번호를 입력하고 확인이 된 경우에만 수정이 가능하다.

<div style="width: 300px; height: 500px;">
    <img src="https://github.com/kyu9341/HairShopApplication/blob/master/pictures/map.png" style="width: 400px
    ; height: 500px;">
</div>
- 하단 바에 지도 버튼을 선택하면 주변 미용실을 찾을 수 있다.  
