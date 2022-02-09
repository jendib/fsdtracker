import {USER_ERROR, USER_REQUEST, USER_SUCCESS} from "../actions/user";
import Vue from "vue";
import {AUTH_LOGOUT} from "../actions/auth";
import axios from "axios";

const state = { status: "", profile: {} };

const getters = {
  getProfile: state => state.profile,
  isProfileLoaded: state => !!state.profile.name
};

const actions = {
  [USER_REQUEST]: ({ commit, dispatch }) => {
    commit(USER_REQUEST);
    axios.get('/user/me')
      .then(response => {
        commit(USER_SUCCESS, response.data);
      })
      .catch(() => {
        commit(USER_ERROR);
        // if resp is unauthorized, logout, to
        dispatch(AUTH_LOGOUT);
      });
  }
};

const mutations = {
  [USER_REQUEST]: state => {
    state.status = "loading";
  },
  [USER_SUCCESS]: (state, data) => {
    state.status = "success";
    Vue.set(state, "profile", data);
  },
  [USER_ERROR]: state => {
    state.status = "error";
  },
  [AUTH_LOGOUT]: state => {
    state.profile = {};
  }
};

export default {
  state,
  getters,
  actions,
  mutations
};