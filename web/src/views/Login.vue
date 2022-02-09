<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" md="6" xl="3">
        <v-card class="pa-10 mt-10">
          <h1 class="text-center font-weight-light">Sign In</h1>

          <v-form
              ref="form"
              v-model="valid">
            <v-text-field
                v-model="name"
                label="E-mail"
                required
            ></v-text-field>

            <v-text-field
                v-model="password"
                label="Password"
                type="password"
                required
            ></v-text-field>

            <v-btn
                :disabled="!valid"
                color="primary"
                class="mr-4"
                @click="login"
                large
                block>
              Sign In
            </v-btn>
          </v-form>
        </v-card>
      </v-col>
    </v-row>

    <v-snackbar v-model="snackbar" :timeout="6000" :color="snackbarColor" bottom>
      {{ snackbarText }}
      <template v-slot:action="{ attrs }">
        <v-btn
            text
            v-bind="attrs"
            @click="snackbar = false">OK</v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script>
import {AUTH_REQUEST} from "@/store/actions/auth";

export default {
  name: 'Login',

  data: () => ({
    valid: false,
    name: '',
    password: '',
    snackbar: false,
    snackbarColor: 'success',
    snackbarText: '',
  }),

  methods: {
    login() {
      const { name, password } = this;
      this.$store.dispatch(AUTH_REQUEST, { name, password }).then(() => {
        this.$router.push(this.$route.query.redirect || '/')
      }).catch(() => {
        this.snackbar = true;
        this.snackbarText = 'Username or password incorrect';
        this.snackbarColor = 'error';
      })
    }
  }
}
</script>
