# AccountBook
JavaFX, MySQL, JDBC를 사용해 구현한 가계부 애플리케이션

<h3>FXML</h3> 디자인을 구현하는 파일들이며 HTML이랑 유사
<ul>
    <li>main.fxml : calender.fxml,  side.fxml 을 담는 전체 창</li>
    <li>calendar-view.fxml : 달력 메뉴</li>
    <li>calender-modal.fxml : 달력 메뉴에 있는 기록하기 버턴 누르면 나우는 창</li>
    <li>side-view.fxml : 달력 메뉴 옆에 있는 창 </li>
    <li>monthtab-view.fxml : side 메뉴에 있는 달 결산 화면</li>
    <li>monthtab-modal-view.fxml : monthtab에 있는 "더보기" 버턴 누르면 나우는 창</li>
    <li>entry-view.fxml : 내역 리스트에 있는 기록창 하나</li>
    <li>entry-modal.fxml : 기록창 상게 보기 창</li>
</ul>



<h3>Java 클래스 </h3>
<ul>
    <li>Controller : 연동된 FXML 파일 컴포넌트 제어.
        <ul>
            <li>MainController : main.fxml랑 연동되어 전체적인 창관리 및 GUI 업데이트를 함</li>
            <li>CalendarController : calender-view.fxml랑 연동되어 달력 메뉴 기능 구현 

ex ) 기록 버턴</li>
            <li>CalendarModalController : calender-modal.fxml랑 연동되어 기록하기 창 구현</li>
            <li>SideController : side-view.fxml랑 연동되어 side 창 기능 구현 

ex ) 정렬 버턴</li>
            <li>MonthtabController : monthtab-view.fxml랑 연동되어 side창에 있는 달 결산 화면 기능 구현 

ex ) 수익/지출 합 계산 후 표시</li>
            <li>MonthtabModalController : monthtab-modal.fxml랑 연동되어 달 결산 상세보기 창 기능 구현 

ex ) 파이 그래프 생성, 바 차트 생성</li>
            <li>EntryController : entry-view.fxml랑 연동되어 side 창에 있는 내역 리스트에 기록 하나를 제어 

ex ) 더보기 버턴, 분류 표시</li>
            <li>EntryModalController : entry-modal.fxml랑 기록 상세 보기 창 기능 구현 

ex ) 기록 수정, 삭제 버턴</li>
        </ul>
    </li>
    <li>Model
        <ul>
            <li>Calendar : 앱에서 사용되는 데이터 형식</li>
            <li>CalendarService : 데이터베이스 기록 삭제, 수정, 생성 등  전체적인 관리 클래스</li>
            <li>ReloadEvent : 데이터베이스 수정시 발생하는 이벤트, GUI 업데이트 때 사용</li>
        </ul>
    </li>
</ul>

<h3>CSS </h3>
FXML 파일에 있는 컴포넌트 구체적인 디자인 구현
<ul>
    <li>side.css 전체적인 side 창 디자인 담담 (side-view.fxml, entry-view.fxml, monthtab-view.fxml)</li>
    <li>calendar.css : 달력 메뉴 디자인 담담 (calendar-view.fxml) </li>
    <li>calender-modal.css : 기록하기 창 디자인 담당 (calendar-modal.fxml)</li>
    <li>monthtab-modal.css : 달 결산 상게보기 창 디자인 담당 (monthtab-modal.fxml)</li>
    <li>entry-modal.css : 기록 상게보기 창 디자인 담당(entry-modal.fxml)</li>
</ul>
