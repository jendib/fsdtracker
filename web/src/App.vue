<template>
  <v-app>
    <v-app-bar
        outlined
        app
        flat
        color="transparent">
      <v-container class="fill-height text--primary">
        <router-link class="font-weight-bold text-uppercase text-decoration-none" to="/">
          FSD Tracker
        </router-link>

        <div>
          <v-btn class="ml-10"
                 color="primary"
                 outlined
                 rounded
                 to="/admin"
                 text>
            Trips
          </v-btn>
        </div>

        <v-spacer></v-spacer>

        <div v-if="isAuthenticated">
          Logged in as <span class="font-weight-bold">{{getProfile.name}}</span>
          <v-btn text
                 color="primary"
                 outlined
                 rounded
                 class="ml-4"
                 v-on:click="logout()">Logout</v-btn>
        </div>
      </v-container>
    </v-app-bar>

    <v-main>
      <router-view/>
    </v-main>

    <v-footer padless>
      <v-card
          flat
          tile
          width="100%"
          color="grey lighten-5"
          class="text-center">
        <v-card-text class="grey--text mt-4 mb-4">
          <p>FSD Tracker is fan-run and is not an official Tesla website</p>
          <v-btn class="mx-4 grey--text" icon title="Source code available on GitHub" href="https://github.com/jendib/fsdtracker/">
            <v-icon size="24px" color="black">mdi-github</v-icon>
          </v-btn>
          <v-btn class="mx-4 grey--text" icon title="CYBRLFT YouTube channel" href="https://www.youtube.com/c/CYBRLFT">
            <v-icon size="24px" color="red">mdi-youtube</v-icon>
          </v-btn>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-text class="grey--text mt-4 mb-4">
          {{ new Date().getFullYear() }} — <strong>FSD Tracker</strong>
        </v-card-text>
      </v-card>
    </v-footer>
  </v-app>
</template>

<style>
</style>

<script>
import {USER_REQUEST} from "@/store/actions/user";
import {AUTH_LOGOUT} from "@/store/actions/auth";
import {mapGetters} from "vuex";

export default {
  name: 'App',

  data: () => ({
    //
  }),

  computed: {
    ...mapGetters(["getProfile", "isAuthenticated", "isProfileLoaded"]),
  },

  methods: {
    logout() {
      this.$store.dispatch(AUTH_LOGOUT).then(() => {
        this.$router.push('/')
      })
    }
  },

  created() {
    if (this.$store.getters.isAuthenticated) {
      this.$store.dispatch(USER_REQUEST);
    }
  }
};
</script>
