package uit.ie303.demo.payments;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PaymentsService {

    private final PaymentsRepository repository;

    public PaymentsService(PaymentsRepository repository){
        this.repository = repository;
    }

    public List<Payments> getAllPayments(){
        return this.repository.findAll();
    }

    public Optional<Payments> getPaymentById(Long id){
        if(null == id) return null;
        return this.repository.findById(id);
    }

    public Payments createPayments(Payments payments){

        //In this case, payment can be created many times without id duplication checking

        if(null == payments) return null; //null safety
        return this.repository.save(payments);
    }


    public Payments updatePayments(Long id, Payments payments){
        if(null == id) return null;

        Payments mPayments = this.repository.findById(id).orElse(null);

        if(mPayments !=null){
            mPayments.setAmount(payments.getAmount());
            mPayments.setPayment_date(payments.getPayment_date());
            // mPayments.setPayment_id(payments.getPayment_id());
            mPayments.setPayment_method(payments.getPayment_method());
            mPayments.setPayment_status(payments.getPayment_status());
            mPayments.setTransaction_code(payments.getTransaction_code());

            return this.repository.save(mPayments);
        }

        return null;
    }

    public void deletePayment(Long id){
        if(null == id) return;
        repository.deleteById(id);
    }
}
