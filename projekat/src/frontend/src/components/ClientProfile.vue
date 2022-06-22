<template>
  <head>
      <title>User Profile</title>
  </head>
  <body >     
    <div class="main-client-container" style="min-height:900px;">    
     <div class="profile-main-container">
        <div class="top-user-container">
          <button  class="custom-icon bi bi-trash3"  data-bs-toggle="modal" data-bs-target="#modalRequest"></button> 
          <button v-if="!this.editing" style="margin-right : 16%;margin-left:auto;"  @click="toggleEdit()" class="custom-icon bi bi-pencil-square"></button>      
        </div>
      <div class="info-container">
        <div v-if="!this.editing" >
          <img v-bind:src="'data:image/jpeg;base64,' + picture" style="width: 300px; height: 200px;">
          <br><br>
          <h1 style="">{{user.firstName}} {{user.lastName}} </h1>        
          <p style="max-width: 250px;">E-mail: {{user.email}}</p>
          <p style="max-width: 250px;">Address: {{user.address}}</p>
          <p style="max-width: 250px;">City: {{user.city}}</p>
          <p style="max-width: 250px;">Country: {{user.country}}</p>
          <p style="max-width: 250px;">Phone Number: {{user.phoneNum}} </p>
          <p style="max-width: 250px;">Penalties: {{user.penalties}}</p>
        </div>
        <div v-else >
          <img v-bind:src="'data:image/jpeg;base64,' + picture" style="width: 300px; height: 200px;">     
          <br><br>
          <div style="display:flex; flex-direction:column;max-width:250px;margin-top:10px;margin-bottom:10px;">
            <input  class="form-control" type="file" style="margin-bottom:10px;" @change="onFileSelected" aria-describedby="inputGroupFileAddon04" aria-label="Upload" accept="image/png, image/jpeg">
            <button class="custom-btn button-primary" style="margin-bottom:20px;" @click="onUpload">Upload</button>
          </div>
          
          <div class="input-group mb-3">
            <input v-model="user.firstName" style="max-width: 250px;" placeholder="First Name:" type="text" class="form-control" aria-label="Username" aria-describedby="firstName">
          </div>
          <div class="input-group mb-3">
            <input v-model="user.lastName" style="max-width: 250px;" placeholder="Last Name:" type="text" class="form-control" aria-label="Username" aria-describedby="lastName">
          </div>
          <div class="input-group mb-3">
            <input v-model="user.address" style="max-width: 250px;" placeholder="Address:" type="text" class="form-control" aria-label="Username" aria-describedby="address">
          </div>
          <div class="input-group mb-3">
            <input v-model="user.city" style="max-width: 250px;" type="text" placeholder="City:" class="form-control" aria-label="Username" aria-describedby="city">
          </div>
          <div class="input-group mb-3">
            <country-select class="form-select" style="max-width: 250px;" v-model="user.country" :usei18n="false" :autocomplete="true" :countryName="true"/>
          </div>
          <div class="input-group mb-3">
            <input v-model="user.phoneNum" style="max-width: 250px;" type="text" placeholder="Phone Number:" class="form-control" aria-label="Username" aria-describedby="city">
          </div>
          <div class="save-cancel-container" style="display:flex;flex-direction:row;">
            <button class="custom-btn button-primary" @click="saveEdit()">Save</button>
            <button class="custom-btn button-primary" style="margin-left:115px;" @click="cancelEdit()">Cancel</button>
          </div>
        </div>
        </div>
        <div class="bot-user-container">
         <button  class="custom-btn button-primary" style="min-width: 200px;margin-bottom: 20px;margin-top: 20px" data-bs-toggle="modal" data-bs-target="#modalSubscriptions">My Subscriptions</button>
         <button  class="custom-btn button-primary" style="min-width: 200px;" data-bs-toggle="modal" data-bs-target="#modalDelete">Change Password</button>
        </div>
      
     </div>  
    


    <div class="reservation-container">
      <div style="margin-bottom:50px;">
      <h3 class="main-heading">Active Reservations</h3>
        <div style="display: flex; align-content: space-between; margin: 15px 0 15px 0">
          <div style="width: 70%">
            <input class="input-group-text" type="text" placeholder="Filter by name, Renting Entity, Price..." v-model="filterR"/>
          </div>
          <br>
          <div style="width: 30%">
            <div style="width: 95%; float: right">
              <select class="form-select" v-model="sortTypeR" name="example">
                <option value=0>Place descending</option>
                <option value=1>Place ascending</option>
                <option value=2>Price descending</option>
                <option value=3>Price ascending</option>
              </select>
            </div>
          </div>
        </div>
        <div class="reservation" v-for="(reservation, index) in this.filteredReservations" v-bind:index="index" :key="reservation.id">
          <div class="reservation-picture"><img v-bind:src="'data:image/jpeg;base64,' + reservation.img"></div>
            <div style="display: flex;">
              <div class="reservation-left">
                <div><h5 style="font-weight: bold;">{{reservation.rentingEntityName}}</h5></div>
                <div><b>Address: </b> {{reservation.place}}</div>
                <div><b>Price: </b> {{reservation.price}}</div>
                <div><b>Starting Date: </b> {{reservation.start}}</div>
                <div><b>Duration: </b> {{msToTime(reservation.duration)}}</div>
                <div v-if="reservation.additionalServices.length != 0"> <b>Additional Services: </b>
                  <span v-for="service in reservation.additionalServices.slice(0,-1)" :key="service"> {{service}}, </span>
                  <span>{{reservation.additionalServices.at(-1)}}</span>
                </div>
              </div>
              <div class="reservation-right">
                  <button class="custom-btn button-primary" @click="cancelReservation(reservation, index)">Cancel Reservation</button>
              </div>
            </div>
        </div>
      </div>
    
   
      <h3 class="main-heading">Reservation History</h3>
      <div style="display: flex; align-content: space-between; margin: 15px 0 15px 0">
        <div style="width: 70%">
          <input class="input-group-text" type="text" placeholder="Filter by name, Renting Entity, Price..." v-model="filterH"/>
        </div>
        <br>
        <div style="width: 30%">
          <div style="width: 95%; float: right">
            <select class="form-select" v-model="sortTypeH" name="example">
              <option value=0>Place descending</option>
              <option value=1>Place ascending</option>
              <option value=2>Price descending</option>
              <option value=3>Price ascending</option>
            </select>
          </div>
        </div>
      </div>
   
      <div class="reservation" v-for="(reservationHistory, index) in this.filteredReservationsHistory" v-bind:index="index" :key="reservationHistory.id">
        <div class="reservation-picture"><img v-bind:src="'data:image/jpeg;base64,' + reservationHistory.img"></div>
        <div style="display: flex;">
          <div class="reservation-left">
            <div><h5 style="font-weight: bold;">{{reservationHistory.rentingEntityName}}</h5></div>
            <div><b>Address: </b> {{reservationHistory.place}}</div>
            <div><b>Price: </b> {{reservationHistory.price}}</div>
            <div><b>Starting Date: </b> {{reservationHistory.start}}</div>
            <div><b>Duration: </b> {{msToTime(reservationHistory.duration)}}</div>
            <div v-if="reservationHistory.additionalServices.length != 0"> <b>Additional Services: </b>
              <span v-for="service in reservationHistory.additionalServices.slice(0,-1)" :key="service"> {{service}}, </span>
              <span>{{reservationHistory.additionalServices.at(-1)}}</span>
            </div>
          </div>
          <div class="reservation-right">
            <button style="margin-right: 10px" class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#modalRate" @click="selectRE(reservationHistory)">Rate</button>
            <button class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#modalReport" @click="selectRE(reservationHistory)">Report</button>
          </div>
        </div>
      
      </div>


    </div>

    <div class="modal fade" id="modalRate" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-body">
                <div>
                  <div class="rating"> 
                    <input v-model="rating" type="radio" name="ratingRE" value="5" id="5"><label for="5">☆</label> 
                    <input v-model="rating" type="radio" name="ratingRE" value="4" id="4"><label for="4">☆</label> 
                    <input v-model="rating" type="radio" name="ratingRE" value="3" id="3"><label for="3">☆</label> 
                    <input v-model="rating" type="radio" name="ratingRE" value="2" id="2"><label for="2">☆</label> 
                    <input v-model="rating" type="radio" name="ratingRE" value="1" id="1"><label for="1">☆</label>
                  </div>
                  <textarea v-model="comment" style="height: 100px" type="text" class="form-control" placeholder="Comment..."></textarea><br>
                  <div style="display:flex;flex-direction:row;align-items:center;justify-content:center;">
                    <button class="custom-btn button-primary" style="margin-right:10px;" @click="rateRE(reservationHistory)">Rate renting entity</button>
                    <button class="custom-btn button-primary" @click="rateRO(reservationHistory)">Rate owner</button>
                  </div>
                </div>
           

       
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>

        <div class="modal fade" id="modalReport" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-body">
                <label>Reason for report:</label>
                <div>
                  <textarea v-model="commentReport" style="height: 100px" type="text" class="form-control" placeholder="Comment..."></textarea><br>
                  <div style="display:flex;flex-direction:row;align-items:center;justify-content:center;">
                    <button class="custom-btn button-primary" style="margin-right:10px;" @click="reportRE(reservationHistory)">Report renting entity</button>
                    <button class="custom-btn button-primary" @click="reportRO(reservationHistory)">Report owner</button><br>
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>
      </div>
            <div class="modal fade" id="modalRequest" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-body">
                  <textarea v-model="text" class="form-control" rows="4" cols="50" placeholder="Reason..."/>
                  <button style="margin-right: 20px;margin-top:5px;" class="custom-btn button-primary" @click="sendDeleteRequest()">Submit</button>
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>

      <div class="modal fade" id="modalSubscriptions" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-body">
                  <div class="subscriptions">
                    <div v-for="(subscription, index) in this.subscriptions" 
                        v-bind:index="index" :key="subscription.id" class="subscription">    
                        <div class="subscription-picture"><img v-bind:src="'data:image/jpeg;base64,' + subscription.img"></div>
                        <div style="display: flex;margin-top:20px;">        
                            <div class="reservation-left">                
                            <h3>{{subscription.name}}</h3>
                            </div>
                            <div clas="reservation-right">
                              <button class="custom-btn button-primary" @click="unsubscribe(subscription,index)">Unsubscribe</button>
                            </div>
                        </div>
                    </div>
                  </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>

        <div class="modal fade" id="modalDelete" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-body">
                <div style="display:flex;flex-direction:column;margin-top:20px;margin-bottom:20px;">
                  <div class="input-group mb-3">
                    <input v-model="oldPassword" class="form-control" type="password" placeholder="Old password">
                  </div>
                  <div class="input-group mb-3">
                    <input v-model="newPassword" class="form-control" type="password" placeholder="New password">
                  </div>
                  <div class="input-group mb-3">
                    <input v-model="confirmPassword" class="form-control" type="password" placeholder="Confirm new password">
                  </div>
                  <button style="float:left;margin:auto;" class="custom-btn button-primary" @click="changePassword()">Change</button>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>

  </body>
</template>

<script>
import axios from "axios"
export default {
  name: "ClientProfile",
  data() {
    return {
      user:{},
      backUp:{},      
      selectedFile: null,
      picture: null,
      editing: false,
      validNumber: '',
      phoneNum: null,
      oldPassword:"",
      newPassword:"",
      confirmPassword:"",
      subscriptions:[],
      text: "",
      reservationsHistory:[],
      selectedReservationHistory:{},
      reservations:[],
      filterR:"",
      filterH: "",
      comment:"",
      rating:5,
      sortTypeR:0,
      sortTypeH: 0,
      commentReport:"",

            }
    },
     mounted() {
      axios
        .get("https://renting-buddy-spring.herokuapp.com/api/clients/loggedClient",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} } )
        .then(response => (this.user = response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);
          
          this.$router.push("/loginPage");
        }
       
      });
      axios.get("https://renting-buddy-spring.herokuapp.com/api/users/loggedUser/picture",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.picture=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);
          
          this.$router.push("/loginPage");
        }
       
      });  
      axios.get("https://renting-buddy-spring.herokuapp.com/api/clients/subscriptions",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.subscriptions=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);          
          this.$router.push("/loginPage");
        }
       
      }); 
        axios.get("https://renting-buddy-spring.herokuapp.com/api/clients/loggedClient/reservations",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.reservations=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);          
          this.$router.push("/loginPage");
        }
       
      });
        axios.get("https://renting-buddy-spring.herokuapp.com/api/clients/loggedClient/reservationHistory",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.reservationsHistory=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);          
          this.$router.push("/loginPage");
        }
       
      }); 
   
    },
    computed: {
    filteredReservationsHistory() {
      return this.reservationsHistory.filter(row => {
        return this.includesH(row);
      }).sort((a,b)=>{
        return this.sort(a,b,this.sortTypeH);})
    },
    filteredReservations(){
       return this.reservations.filter(row => {
        return this.includesH(row);
      }).sort((a,b)=>{
        return this.sort(a,b,this.sortTypeR);})
    }},
    methods: {    
    toggleEdit(){
          this.backUp={"firstName":this.user.firstName,"lastName":this.user.lastName,"address":this.user.address,"city":this.user.city,"country":this.user.country,"phoneNum":this.user.phoneNum};
    
          this.editing=true; 
      },

    changePassword(){
      var passwordRegex=/^[0-9A-Za-z]+$/;

      if(this.newPassword.length<5 || this.newPassword.length > 20){
        this.$toast.error("Password must be between 5-20 characters");
        return;
      }
      if(!passwordRegex.test(this.newPassword)){
        this.$toast.error("Password must only contain letters and numbers");
        return;
      }
      if(!passwordRegex.test(this.oldPassword)){
        this.$toast.error("Password must only contain letters and numbers");
        return;
      }
      if(this.newPassword !== this.confirmPassword){
        this.$toast.error("Passwords must match");
        return;
      }

      const passwordChange={"oldPassword":this.oldPassword,"newPassword":this.newPassword,"newPasswordConfirm":this.confirmPassword};
  
      axios.put(`https://renting-buddy-spring.herokuapp.com/api/clients/changePassword`,passwordChange,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response =>{
        if(response.data==true){
          this.$root.accessToken=null;
          this.$root.signedIn=false;
          localStorage.setItem("jwt",null);          
          this.$router.push("/loginPage");
        }else{
          this.$toast.error("Error");
        }
      });

    },

    sendDeleteRequest(){
      if(this.text.length<5){
        this.$toast.error("Provide a reason");
        return;
      }
      const request={"id":0,'submitterId':this.user.id,'submitterUsername':this.user.username,'text':this.text,'type':0};

      axios.post("https://renting-buddy-spring.herokuapp.com/api/users/submitRequest",request,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Request Sent");}else{
          this.$toast.error("Error");
      }})
    },

    unsubscribe(subscription,index){
      axios.put(`https://renting-buddy-spring.herokuapp.com/api/clients/unSubscribeDTO`,subscription,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response =>{
        if(response.data==true){
          this.$toast.success("Unsubscribed");
          this.subscriptions.splice(index,1);
        }else{
          this.$toast.error("Error");
        }
      });
    },

    saveEdit(){
      var regExp = /^[A-Za-z]+$/;
      
      if (this.user.firstName.length < 2 || this.user.firstName.length > 20) {
        this.$toast.error("First name must be between 2-20 characters");
        return;
        }
      if(regExp.test(this.user.firstName)==false){
        this.$toast.error("First name must contain only letters");
        return;
      }
      this.user.firstName=this.user.firstName.charAt(0).toUpperCase() + this.user.firstName.slice(1);
      
      if(this.user.lastName.length < 2 || this.user.lastName.length > 20){
        this.$toast.error("Last name must be between 2-20 characters");
        return;
      }
      if(regExp.test(this.user.lastName)==false){
        this.$toast.error("Last name must contain only letters");
        return;
      }
      this.user.lastName=this.user.lastName.charAt(0).toUpperCase() + this.user.lastName.slice(1);
      if(this.user.address === ''){
        this.$toast.error("Provide address");
        return;
      }
      if(this.user.city === ''){
        this.$toast.error("Provide city name");
        return;
      } 
      
      if(this.country == ""){
        this.$toast.error("Provide country");
        return;
      }
      var phoneRegex= /^\+?\d{8,13}$/;
      if(this.user.phoneNum ===''){
        this.$toast.error("Provide a phone number");
        return;
      }
          
      if(phoneRegex.test(this.user.phoneNum)===false){
        this.$toast.error("Invalid phone number");
        return;
      }
      
      axios.put("https://renting-buddy-spring.herokuapp.com/api/clients/loggedClient",this.user,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => this.user=response.data ).then(this.toastMessage());
      this.editing=false;
    },
    cancelEdit(){
      this.user.firstName=this.backUp.firstName;
      this.user.lastName=this.backUp.lastName;
      this.user.address=this.backUp.address;
      this.user.city=this.backUp.city;
      this.user.country=this.backUp.country;
      this.user.phoneNum=this.backUp.phoneNum;
      this.editing=false;
    },
    toastMessage(){
      this.$toast.success("Info updated");
    },
    onFileSelected(event){
      this.selectedFile= event.target.files[0];
    },
    onUpload(){
      if(this.selectedFile == null){
          this.$toast.info("Select a file first ...");
      }else{
      if(this.selectedFile.size > 1048576){
        this.$toast.info("File is too large ...\nMust be smaller than 1Mb");
      }else{
      const fd=new FormData();
      fd.append('image',this.selectedFile,this.selectedFile.name);
      axios.put("https://renting-buddy-spring.herokuapp.com/api/users/loggedClient/picture",fd,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} },{
        onUploadProgress: uploadEvent => {
          console.log("Upload Progress: "+ Math.round(uploadEvent.loaded/uploadEvent.total * 100) + '%');
        }
      }).then(response => { 
        this.picture=response.data;
        this.$root.loggedPicture=response.data;
        })
    }}
    },
    dateFromLocal(dateString){
      if(dateString==null){
        return new Date();
      }
      const parts=dateString.split(" ");
      if( parts.length !=2){
        return new Date();
      }
      const dateParts= parts[0].split(".");
      
      if( dateParts.length !=3){
        return new Date();
      }
      const hourParts=parts[1].split(":");
      
      if( hourParts.length !=2){
        return new Date();
      }
      
      const day = dateParts[0];
      const month = dateParts[1];
      const year = dateParts[2];
      const hours = hourParts[0];
      const minutes = hourParts[1];
      // new Date(year, month, day, hours, minutes, seconds, milliseconds)
      const date= new Date(year,month-1,day,hours,minutes);
      return date;
    }, 
    selectRE(rentingEntity){

      this.selectedReservationHistory=rentingEntity;
    },
    cancelReservation(reservation, index){
      
      const date=this.dateFromLocal(reservation.start);
      let dateCheck=new Date();
      
      dateCheck.setDate(dateCheck.getDate() + 3);

      if(date < dateCheck){
        this.$toast.error("You can not cancel the reservation within the 3 days");
      }else{
        axios.put("https://renting-buddy-spring.herokuapp.com/api/clients/cancelReservation",reservation,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {
          if(response.data == true){
            this.$toast.success("You have successfully canceled the reservation");
            this.reservations.splice(index,1);
          }else{
            this.$toast.error("You can not cancel the reservation within the 3 days");
          }
        })
        
      }
    },

    msToTime(seconds) {     
      seconds = Number(seconds)/1000;
      var d = Math.floor(seconds / (3600*24));
      var h = Math.floor(seconds % (3600*24) / 3600);

      var dDisplay = d > 0 ? d + (d == 1 ? " day " : " days ") : "";
      if (h != 0) {
        dDisplay = d > 0 ? d + (d == 1 ? " day, " : " days, ") : "";
        var hDisplay = h > 0 ? h + (h == 1 ? " hour " : " hours ") : "";
        return dDisplay + hDisplay;
      }

      return dDisplay
  },
  rateRE(){
    const reservationHistory=this.selectedReservationHistory;
    const review={"id":0,'rentingEntityId':reservationHistory.rentingEntityId,'rentingOwnerId':reservationHistory.rentingEntityOwnerId,'clientId':0,'rating':this.rating,'comment':this.comment};
    axios.post("https://renting-buddy-spring.herokuapp.com/api/clients/addReviewRE",review,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Review Added");this.comment="";}else{
      this.$toast.error("Error");
    }})
  },
  rateRO(){
    const reservationHistory=this.selectedReservationHistory;
    const review={"id":0,'rentingEntityId':reservationHistory.rentingEntityId,'rentingOwnerId':reservationHistory.rentingEntityOwnerId,'clientId':0,'rating':this.rating,'comment':this.comment};
    axios.post("https://renting-buddy-spring.herokuapp.com/api/clients/addReviewRO",review,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Review Added");this.comment="";}else{
      this.$toast.error("Error");
    }})

  },
  reportRE(){
    if(this.commentReport.length<5){
        this.$toast.error("Provide a valid reason");
        return;
      }
    const reservationHistory=this.selectedReservationHistory;
    const complaint={"id":0,"complainantId":0,"rentingEntityId":reservationHistory.rentingEntityId,"respodentId":0,"text":this.commentReport};
    axios.post("https://renting-buddy-spring.herokuapp.com/api/clients/addComplaintRE",complaint,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Complaint Sent");this.commentReport="";}else{
          this.$toast.error("Error");
        }})
  },
  reportRO(){
    if(this.commentReport.length<5){
        this.$toast.error("Provide a valid reason");
        return;
      }
    const reservationHistory=this.selectedReservationHistory;
    const complaint={"id":0,"complainantId":0,"rentingEntityId":0,"respodentId":reservationHistory.rentingEntityOwnerId,"text":this.commentReport};
    axios.post("https://renting-buddy-spring.herokuapp.com/api/clients/addComplaintRO",complaint,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Complaint Sent");this.commentReport="";}else{
          this.$toast.error("Error");
        }})
  },
  includesH(row){
        
        const name = row.place.toString().toLowerCase();
        const rentingEntityName = row.rentingEntityName.toString().toLowerCase();
        const price = row.price.toString().toLowerCase();
        const searchTerm = this.filterH.toLowerCase();
        return (name.includes(searchTerm) || rentingEntityName.includes(searchTerm) || price.includes(searchTerm))
        
    },
    sort(a,b,sortType){
        if(sortType==0){
          let fa = a.place.toLowerCase(), fb = b.place.toLowerCase();
          if (fa < fb) {
            return -1
          }
          if (fa > fb) {
            return 1
          }
          return 0
        }
        else if (sortType== 1){
          let fa = a.place.toLowerCase(), fb = b.place.toLowerCase();
           if (fa > fb) {
            return -1
          }
          if (fa < fb) {
            return 1
          }
          return 0
        }
        if(sortType==2){
        let fa =parseInt(a.price), fb =parseInt(b.price)
        
          if (fa > fb) {
            return -1
          }
          if (fa < fb) {
            return 1
          }
          return 0
        }
        else if (sortType == 3){
          
          let fa = parseInt(a.price), fb =parseInt(b.price);
          
           if (fa < fb) {
            return -1
          }
          if (fa > fb) {
            return 1
          }
          return 0
        }

    },
  }

  
}
</script>

<style scoped>

.main-client-container{
  display: flex; 
  flex-direction: row;
  padding-top: 50px;
  padding-left: 80px;
  padding-right: 10px;
}

.profile-main-container{
  flex-grow: 1;
}

.top-user-container{
  display: flex;
  flex-direction: row;
}

.bot-user-container{
  display: flex;
  flex-direction: column;
  align-items: left;
  margin-right: 50%;
}

.custom-icon{
    color: white;
    min-height: 50px;
    min-width: 50px;
    background-color: #00587a;
    display: inline-block;
    font-weight: 400;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    border: 1px solid transparent;
    padding: 0.375rem 0.75rem;
    font-size: 1rem;
    line-height: 1.5;
    border-radius: 0.25rem;
    transition: color 0.3s ease-in-out, background-color 0.3s ease-in-out, border-color 0.3s ease-in-out, box-shadow 0.3s ease-in-out;

}


.custom-icon:hover {
    background-color: #196887;
}

.custom-icon:active {
    border-color: #bbb;
    color: #bbb;
    background-color: #004661;
}

.reservation-container{
  flex-grow: 9;
  margin-left: 50px;
}

.subscriptions{
  display: flex;
  flex-direction: column;
}

.info-container{
  display: flex;
  flex-direction: column;
  align-self: center;
  margin-top: 5px;
  border: 1px solid transparent;
  border-color: #004661;
  margin-right: 80px;
  padding-left: 5px;
  padding-bottom: 5px;
  padding-top: 5px;
}

.reservation {
  text-align: center;
  width: 100%;
  height: 170px;
  border: 1px solid darkgrey;
  border-radius: 5px;
  overflow: hidden;
  margin-bottom: 10px;
}

.subscription{
  text-align: center;
  width: 100%;
  height: 100px;
  border: 1px solid darkgrey;
  border-radius: 5px;
  overflow: hidden;
  margin-bottom: 10px;

}

.reservation-picture img {
  object-fit: cover;
  margin: 10px;
  border-radius: 5px;
  width: 200px;
  height: 150px;
}

.reservation-picture {
  float:left;
}


.reservation-left {
  float: left;
  margin: 10px;
  text-align: left;
  width: 60%;
}

.reservation-right {
  float: right;
  margin: auto;
  text-align: center;
  width: 30%;
  overflow: hidden;
}

.subscription-picture img {
  object-fit: cover;
  margin: 10px;
  border-radius: 5px;
  width: 100px;
  height: 75px;
}

.subscription-picture {
  float:left;
}

.rating {
    display: flex;
    flex-direction: row-reverse;
    justify-content: center
}

.rating>input {
    display: none
}

.rating>label {
    position: relative;
    width: 1em;
    font-size: 30px;
    font-weight: 300;
    color: #FFD600;
    cursor: pointer
}

.rating>label::before {
    content: "\2605";
    position: absolute;
    opacity: 0
}

.rating>label:hover:before,
.rating>label:hover~label:before {
    opacity: 1 !important
}

.rating>input:checked~label:before {
    opacity: 1
}

.rating:hover>input:checked~label:before {
    opacity: 0.4
}

.rating-submit{
	border-radius: 15px;
	color: #fff;
	height: 49px;
}

[type=radio]:checked + label {
  background-color: transparent;
}



.rating-submit:hover{
	
	color: #fff;
}

</style>