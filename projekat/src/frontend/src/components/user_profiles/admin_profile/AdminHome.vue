<template>
  <nav>
    <div class="nav nav-tabs justify-content-center mt-2 mb-3" id="nav-tab" role="tablist">
      <button class="nav-link active nav-button" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-profile" type="button"
              role="tab" aria-controls="nav-profile" aria-selected="true" @Click="routeProfile">Profile
      </button>
      <button class="nav-link nav-button" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-entities" type="button"
              role="tab" aria-controls="nav-entities" aria-selected="false" @Click="routeEntities">Entities
      </button>
      <button class="nav-link nav-button" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-reviews" type="button"
              role="tab" aria-controls="nav-reviews" aria-selected="false" @Click="routeReviews">Reviews
      </button>
      <button class="nav-link nav-button" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-complaints" type="button"
              role="tab" aria-controls="nav-complaints" aria-selected="false" @Click="routeComplaints">Complaints
      </button>
      <button class="nav-link nav-button" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-requests" type="button"
              role="tab" aria-controls="nav-requests" aria-selected="false" @Click="routeRequests">Requests
      </button>
      <button class="nav-link nav-button" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-reports" type="button"
              role="tab" aria-controls="nav-reports" aria-selected="false" @Click="routeReports">Reports
      </button>
      <button class="nav-link nav-button" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-register" type="button"
              role="tab" aria-controls="nav-register" aria-selected="false" @Click="routeRegister">Register
      </button>
      <button class="nav-link nav-button" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-fee" type="button"
              role="tab" aria-controls="nav-fee" aria-selected="false" @Click="routeFee">Fee
      </button>
      <button class="nav-link nav-button" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-reservations"
              type="button" role="tab" aria-controls="nav-reservations" aria-selected="false" @Click="routeReservations">Reservations
      </button>
      <button class="nav-link nav-button" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-loyalty" type="button"
              role="tab" aria-controls="nav-loyalty" aria-selected="false" @Click="routeLoyalty">Loyalty
      </button>
    </div>
  </nav>

  <div class="tab-content" id="nav-tabContent" style="width: 60%; margin: auto; text-align: center">
    <div class="tab-pane fade show active" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
      <router-view></router-view>
    </div>
    <div class="tab-pane fade" id="nav-entities" role="tabpanel" aria-labelledby="nav-entities-tab">
      <router-view></router-view>
    </div>
    <div class="tab-pane fade" id="nav-reviews" role="tabpanel" aria-labelledby="nav-reviews-tab">
      <router-view></router-view>
    </div>
    <div class="tab-pane fade" id="nav-complaints" role="tabpanel" aria-labelledby="nav-complaints-tab">
      <router-view></router-view>
    </div>
    <div class="tab-pane fade" id="nav-requests" role="tabpanel" aria-labelledby="nav-requests-tab">
      <router-view></router-view>
    </div>
    <div class="tab-pane fade" id="nav-reports" role="tabpanel" aria-labelledby="nav-reports-tab">
      <router-view></router-view>
    </div>
    <div class="tab-pane fade" id="nav-register" role="tabpanel" aria-labelledby="nav-register-tab">
      <router-view></router-view>
    </div>
    <div class="tab-pane fade" id="nav-fee" role="tabpanel" aria-labelledby="nav-fee-tab">
      <router-view></router-view>
    </div>
    <div class="tab-pane fade" id="nav-reservations" role="tabpanel" aria-labelledby="nav-reservations-tab">
      <router-view></router-view>
    </div>
    <div class="tab-pane fade" id="nav-loyalty" role="tabpanel" aria-labelledby="nav-loyalty-tab">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>

export default {
  name: "AdminProfile",
  data() {
    return {
      chosenRoute: null
    }
  },

  mounted() {
    this.routeProfile();
  },

  methods: {
    routeProfile() {
      this.$router.push({name: "adminProfile"});
    },

    routeEntities() {
      this.$router.push({name: "adminEntities"});
    },

    routeReviews() {
      this.$router.push({name: "adminReviews"});
    },

    routeComplaints() {
      this.$router.push({name: "adminComplaints"});
    },

    routeRequests() {
      this.$router.push({name: "adminRequests"});
    },

    routeReports() {
      this.$router.push({name: "adminReports"});
    },

    routeRegister() {
      this.$router.push({name: "adminRegister"});
    },

    routeFee() {
      this.$router.push({name: "adminFee"});
    },

    routeReservations() {
      this.$router.push({name: "adminReservations"});
    },

    routeLoyalty () {
      this.$router.push({name: "adminLoyalty"});
    }
  }
}
</script>

<style scoped>

.nav-button {
  color: #00587a;
  font-weight: bold;
}

</style>