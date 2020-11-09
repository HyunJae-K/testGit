package board;
import java.io.Serializable;
import java.sql.Timestamp;


//DTO(Data Transfer Object)는 VO(Value Object)로 바꿔 말할 수 있는데 
//계층간 데이터 교환을 위한 자바빈즈를 말합니다.
public class BoardDTO implements Serializable{

	private int num; 
    private String writer;
    private String subject;
    private String email;
    private String content;
    private String passwd;
    private String boardid;
    private Timestamp reg_date;
    private int readcount;
    private String ip;
    private int ref;
    private int re_step;	
    private int re_level;
    private String filename;
    private int filesize;
     
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	public String getBoardid() {
		return boardid;
	}
	public void setBoardid(String boardid) {
		this.boardid = boardid;
	}
	public void setNum(int num){
    	this.num=num;
    }
    public void setWriter (String writer) {
        this.writer = writer;
    }
    public void setSubject (String subject) {
        this.subject = subject;
    }
    public void setEmail (String email) {
        this.email = email;
    }
    public void setContent (String content) {
        this.content = content;
    }
    public void setPasswd (String passwd) {
        this.passwd = passwd;
    }
    public void setReg_date (Timestamp reg_date) {
        this.reg_date = reg_date;
    }
	public void setReadcount(int readcount){
	  	this.readcount=readcount;
	}
    public void setIp (String ip) {
        this.ip = ip;
    }
	public void setRef (int ref) {
        this.ref = ref;
    }
	public void setRe_level (int re_level) {
        this.re_level=re_level;
    }
	public void setRe_step (int re_step) {
        this.re_step=re_step;
    }
    
    public int getNum(){
    	return num;
    }
    public int getReadcount(){
   	    return readcount;
    }
    public String getWriter () {
        return writer;
    }
    public String getSubject () {
        return subject;
    }
    public String getEmail () {
        return email;
    }
    public String getContent () {
        return content;
    }
    public String getPasswd () {
        return passwd;
    }
    public Timestamp getReg_date () {
        return reg_date;
    }
    public String getIp () {
        return ip;
    }
    public int getRef () {
        return ref;
    }
	public int getRe_level () {
        return re_level;
    }
	public int getRe_step () {
        return re_step;
    }
	@Override
	public String toString() {
		return "BoardDataBean [num=" + num + ", writer=" + writer
				+ ", subject=" + subject + ", email=" + email + ", content="
				+ content + ", passwd=" + passwd + ", boardid=" + boardid
				+ ", reg_date=" + reg_date + ", readcount=" + readcount
				+ ", ip=" + ip + ", ref=" + ref + ", re_step=" + re_step
				+ ", re_level=" + re_level + ", filename=" + filename
				+ ", filesize=" + filesize + "]";
	}
	
    
}