import { useEffect, useState } from "react";
import Table from "react-bootstrap/Table";
import "./App.css";
import api from "./api";
import Pagination from "react-bootstrap/Pagination";

function App() {
  // 데이터를 가져와서 사용할 state
  const [users, setUsers] = useState([]);

  // 현재 페이지 번호 state
  const [page, setPage] = useState(0);

  // 전체 페이지 수를 저장할 state
  const [totalPages, setTotalPages] = useState(0);

  // 페이지 그룹 수
  const pageSize = 8;

  // 처음 페이지가 로딩되면 DB에서 api 요청하기
  useEffect(() => {
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
  }, [page]); // page 값이 바뀔 때 마다 실행

  // 한 화면에 10개 페이지씩 보여주기 처리
  // 0 ~ 9 page : 0 그룹 (1 ... 10)
  // 10 ~ 19 page : 1 그룹 (11 ... 20)
  //    ...           ...
  // 마지막 그룹 : 마지막 페이지와 전체 페이지 수 중 작은 값 선택

  const pageGroup = Math.floor(page / pageSize);
  const startPage = pageGroup * pageSize;
  const endPage = Math.min(startPage + pageSize, totalPages);

  return (
    <div className="container py-5">
      <div className="text-center mb-5">
        <h1 className="display-4 fw-bold text-primary">User 목록</h1>
        <p className="text-secondary">
          Spring Boot + React + JPA Sample Project
        </p>
      </div>
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
