package com.springboot2.springboot2.example;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExampleService {

    private final ExampleModelRepository exampleModelRepository;

    public ExampleModel createData(ExampleRequest request) {
//        ExampleModel exampleModel = new ExampleModel();
//        exampleModel.setName(request.getName());Ò
//        exampleModel.setEmail(request.getEmail());
//        exampleModel.setPhone(request.getPhone());
//        exampleModelRepository.save(exampleModel);
//
//        return exampleModel;
        return exampleModelRepository.save(ExampleModel.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build()
        );
    }

    public List<ExampleModel> getAllData() {
        return exampleModelRepository.findAll();
    }

    public ExampleModel updateData(Long id, ExampleModel request) throws BadRequestException {
//        Optional<ExampleModel> data = exampleModelRepository.findById(id);
//        if (data.isPresent()) {
//            ExampleModel exampleModel = data.get();
//            exampleModel.setName(request.getName());
//            exampleModel.setEmail(request.getEmail());
//            exampleModel.setPhone(request.getPhone());
//            exampleModelRepository.save((exampleModel));
//            return exampleModel;
//        } else {
//            throw new BadRequestException();
//        }

        return exampleModelRepository.findById(id)
                .map(exampleModel -> {
                            ExampleModel updateBuilder = ExampleModel.builder()
                                    .id(exampleModel.getId())
                                    .name(request.getName())
                                    .email(request.getEmail())
                                    .phone(request.getPhone())
                                    .build();
                            exampleModelRepository.save(updateBuilder);
                            return updateBuilder;
                        }
                ).orElseThrow(BadRequestException::new);

    }

    public void deleteData(Long id) {
        exampleModelRepository.deleteById(id);

    }
}
