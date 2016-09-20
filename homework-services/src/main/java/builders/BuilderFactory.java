package builders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

public class BuilderFactory{

	@Autowired
	private AutowireCapableBeanFactory autowireBeanFactory;

	
	public TransactionBuilder getObject(){
		TransactionBuilder createdBean = new TransactionBuilder();
		autowireBeanFactory.autowireBean(createdBean);

		return createdBean;
	}

//	@Override
//	public Class<?> getObjectType() {
//		// TODO Auto-generated method stub
//		return TransactionBuilder.class;
//	}
//
//	@Override
//	public boolean isSingleton() {
//		// TODO Auto-generated method stub
//		return false;
//	}

}
