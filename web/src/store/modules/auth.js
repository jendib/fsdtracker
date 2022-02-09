import {AUTH_ERROR, AUTH_LOGOUT, AUTH_REQUEST, AUTH_SUCCESS} from "../actions/auth";
import {USER_REQUEST} from "../actions/user";
import axios from "axios";

const state = {
  token: localStorage.getItem("user-token") || "",
  status: "",
};

if (state.token) {
  axios.defaults.headers.common['Authorization'] = 'Bearer ' + state.token
}

const getters = {
  isAuthenticated: state => !!state.token,
};

const actions = {
  [AUTH_REQUEST]: ({ commit, dispatch }, user) => {
    return new Promise((resolve, reject) => {
      commit(AUTH_REQUEST);
      axios.post('/user/login', user)
        .then(response => {
          localStorage.setItem("user-token", response.data.token);
          axios.defaults.headers.common['Authorization'] = 'Bearer ' + response.data.token
          commit(AUTH_SUCCESS, response.data);
          dispatch(USER_REQUEST);
          resolve(response.data);
        })
        .catch(err => {
          commit(AUTH_ERROR, err);
          localStorage.removeItem("user-token");
          reject(err);
        });
    });
  },
  [AUTH_LOGOUT]: ({ commit }) => {
    return new Promise(resolve => {
      commit(AUTH_LOGOUT);
      localStorage.removeItem("user-token");
      resolve();
    });
  }
};

const mutations = {
  [AUTH_REQUEST]: state => {
    state.status = "loading";
  },
  [AUTH_SUCCESS]: (state, data) => {
    state.status = "success";
    state.token = data.token;
  },
  [AUTH_ERROR]: state => {
    state.status = "error";
  },
  [AUTH_LOGOUT]: state => {
    state.token = "";
  }
};

export default {
  state,
  getters,
  actions,
  mutations
};