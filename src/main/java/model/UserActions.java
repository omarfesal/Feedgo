package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class UserActions implements Serializable {
    @Id @GeneratedValue
    private int action_number;
    @Column(nullable = false,length = 8)
    private int UserId;
    @Column(nullable = false,length = 8)
    private int SourceId;
    @Column(nullable = true,length = 20)
    private String Comment;
    @Column(nullable = true , length = 20)
    private double rate_number;
    @Column(nullable = true , length = 20)
    private boolean visited;    

    public int getAction_number() {
        return action_number;
    }

    public void setAction_number(int action_number) {
        this.action_number = action_number;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public int getSourceId() {
        return SourceId;
    }

    public void setSourceId(int SourceId) {
        this.SourceId = SourceId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }

    public double getRate_number() {
        return rate_number;
    }

    public void setRate_number(double rate_number) {
        this.rate_number = rate_number;
    }


    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    
   
    
    
    
    
}
