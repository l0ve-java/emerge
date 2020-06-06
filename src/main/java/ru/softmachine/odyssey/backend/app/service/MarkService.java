package ru.softmachine.odyssey.backend.app.service;

import ru.softmachine.odyssey.backend.app.dto.Mark;

import java.util.ArrayList;
import java.util.List;

public class MarkService {
    private List<Mark> allMarks = new ArrayList<>();

    public void addMark(Mark mark) {
        allMarks.add(mark);
    }
}
