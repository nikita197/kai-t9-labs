package courseworkRIS.sqlDataProvider;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Cars")
public class Cars {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	public String Name;
}
