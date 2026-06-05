import { useEffect, useState } from "react";
import { Table, Pagination, Form, Button } from "react-bootstrap";
import "./App.css";
import api from "./api";

function App() {
  // 데이터를 가져와서 사용할 state
  const [users, setUsers] = useState([]);

  // 현재 페이지 번호 state
  const [page, setPage] = useState(0);

  // 전체 페이지 수를 저장할 state
  const [totalPages, setTotalPages] = useState(0);

  // 페이지 그룹 수
  const pageSize = 10;

  // 처음 페이지가 로딩되면 DB에서 api 요청하기

  // 리스트를 불러오는 함수
  function fetchUsers() {
    api
      .get(`/getPage?page=${page}&size=${pageSize}`)
      .then((res) => {
        console.log(res.data);
        setUsers(res.data.content);
        setTotalPages(res.data.totalPages);
      })
      .catch((err) => {
        console.log(err);
      });
  }

  useEffect(() => {
    fetchUsers();
  }, [page]); // page 값이 바뀔 때 마다 실행

  // 한 화면에 10개 페이지씩 보여주기 처리
  // 0 ~ 9 page : 0 그룹 (1 ... 10)
  // 10 ~ 19 page : 1 그룹 (11 ... 20)
  //    ...           ...
  // 마지막 그룹 : 마지막 페이지와 전체 페이지 수 중 작은 값 선택

  const pageGroup = Math.floor(page / pageSize);
  const startPage = pageGroup * pageSize;
  const endPage = Math.min(startPage + pageSize, totalPages);

  // 신규 데이터 추가용 작업
  // 추가할 사용자의 폼과 연결될 state 선언
  const [form, setForm] = useState({
    name: "",
    gender: "Male",
    email: "",
    likeColor: "",
  });

  // 사용자 입력값을 form state에 저장하는 함수
  // e : 각 컨트롤에 입력되거나 선택된 값
  function handleChange(e) {
    const name = e.target.name;
    const value = e.target.value;

    // 입력값을 form state에 수정
    setForm({ ...form, [name]: value });
  }

  // 사용자 추가 단추 클릭 시 처리할 이벤트
  // async : 비동기 함수
  async function handleSubmit(e) {
    e.preventDefault(); // 중간에 입력한 자료를 유지
    try {
      await api.post("/users", form); // post 방식으로 /users url로 form을 담아서 전송

      alert("사용자가 추가되었습니다.");

      // form state 초기화
      setForm({
        name: "",
        gender: "Male",
        email: "",
        likeColor: "",
      });

      // 맨 앞의 페이지로 이동해서 화면에 뿌린다.(useEffect()에서 fetchUsers()가 호출됨)
      setPage(0);

      // 페이지 로드 함수를 호출(이미 페이지가 0인 경우에도 호출되도록)
      fetchUsers();
    } catch (error) {
      console.log(error);
      alert("사용자 추가 실패");
    }
  }

  return (
    <div className="container py-5">
      <div className="text-center mb-5">
        <h1 className="display-4 fw-bold text-primary">User 목록</h1>
        <p className="text-secondary">
          Spring Boot + React + JPA Sample Project
        </p>
      </div>

      <Form onSubmit={handleSubmit} className="mb-5 border rounded p-4">
        <h4 className="mb-3">사용자 추가</h4>
        <Form.Group className="mb-3">
          <Form.Label>이름</Form.Label>
          <Form.Control
            type="text"
            name="name"
            placeholder="이름 입력"
            value={form.name}
            // 함수를 호출할 때 아래와 같이 호출해도 되고 그냥 함수명만 호출해도 된다.
            onChange={(e) => handleChange(e)}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>성별</Form.Label>
          <Form.Select
            name="gender"
            value={form.gender}
            onChange={handleChange}
          >
            <option value="Male">Male</option>
            <option value="Female">Female</option>
          </Form.Select>
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>이메일</Form.Label>
          <Form.Control
            type="email"
            name="email"
            placeholder="이메일 입력 (abc@def.com)"
            value={form.email}
            onChange={handleChange}
          />
        </Form.Group>
        <Form.Group className="mb-3">
          <Form.Label>좋아하는 색상</Form.Label>
          <Form.Control
            type="text"
            name="likeColor"
            placeholder="색상 입력"
            value={form.likeColor}
            onChange={handleChange}
          />
        </Form.Group>
        <Button type="submit" variant="primary">
          추가하기
        </Button>
      </Form>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th>#</th>
            <th>Name</th>
            <th>Email</th>
            <th>Like Color</th>
            <th>Created At</th>
            <th>Updated At</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => {
            return (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.name}</td>
                <td>{user.email}</td>
                <td>{user.likeColor}</td>
                <td>{new Date(user.createdAt).toLocaleDateString("ko-KR")}</td>
                <td>{new Date(user.updatedAt).toLocaleDateString("ko-KR")}</td>
              </tr>
            );
          })}
        </tbody>
      </Table>
      {/* Pagination 시작 */}
      <div className="d-flex justify-content-center mt-4">
        <Pagination>
          <Pagination.First
            // page == 0 이면 disabled = true
            disabled={page == 0}
            onClick={() => {
              if (page > 0) setPage(0);
            }}
          />
          <Pagination.Prev
            disabled={page == 0}
            onClick={() => {
              if (page > 0) setPage(page - 1);
            }}
          />
          {[...Array(endPage - startPage)].map((_, index) => {
            const pageNumber = startPage + index;
            return (
              <Pagination.Item
                key={pageNumber}
                active={page == pageNumber}
                onClick={() => setPage(pageNumber)}
              >
                {pageNumber + 1}
                {/* 페이지 번호를 클릭하면 현재 index => page state */}
              </Pagination.Item>
            );
          })}
          <Pagination.Next
            // page == 0 이면 disabled = true
            disabled={page == totalPages - 1}
            onClick={() => {
              if (page < totalPages - 1) setPage(page + 1);
            }}
          />
          <Pagination.Last
            // page == totalPages 이면 disabled = true
            disabled={page == totalPages - 1}
            onClick={() => {
              if (page < totalPages - 1) setPage(totalPages - 1);
            }}
          />
        </Pagination>
      </div>
      {/* Pagination 끝 */}
    </div>
  );
}

export default App;
