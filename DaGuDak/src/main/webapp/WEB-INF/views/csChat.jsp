<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file = "/WEB-INF/views/common/head.jsp" %>


   <section class="ftco-section contact-section ftco-no-pb" id="contact-section">
      <div class="container">
         <div class="row justify-content-center mb-5 pb-3">
            <div class="col-md-7 heading-section text-center ftco-animate">
               <span class="subheading">CUSTOMER SERVICE</span>
               <h2 class="mb-4">1:1 문의하기</h2>
               <p>빠른 시일 내에 답변드리겠습니다.</p>
            </div>
         </div>

         <div class="row block-9" style="margin:auto; margin-bottom: 50px; width: 60%;">
            <div>
            <!-- form -->
               <form action="#" class="p-4 p-md-5 contact-form" >
                  <div class="row" >
                  
                     <div class="col-md-12">
                        <div class="form-group">
                           <input type="text" name="id" class="form-control" placeholder="제목" required>
                        </div>
                     </div>
                     
                     <div class="col-md-12">
                        <div class="form-group">
                           <textarea name="content" id="content" cols="30" rows="7" class="form-control" placeholder="문의 내용 입력"></textarea>
                        </div>
                     </div>
                     
                    
                     <div style="margin-left: auto;  margin-right: auto; margin-top: 20px;">
                        <div class="form-group">
                           <input type="submit" value="문의하기" class="btn btn-primary py-3 px-5">
                        </div>
                     </div>
                  </div>
               </form>
               
            </div>

            
         </div>
      </div>
      
   </section>
   
<%@ include file = "/WEB-INF/views/common/footer.jsp" %>