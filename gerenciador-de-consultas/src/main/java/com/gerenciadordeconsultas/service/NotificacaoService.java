package com.gerenciadordeconsultas.service;

import com.gerenciadordeconsultas.entity.Consulta;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

    public void enviarConfirmacaoAgendamento(Consulta consulta) {
        System.out.printf("Notificação: Consulta agendada para %s com Dr. %s%n",
                consulta.getDataHora(),
                consulta.getMedico().getNome());
    }

    public void enviarCancelamentoConsulta(Consulta consulta) {
        System.out.printf("Notificação: Consulta cancelada para %s com Dr. %s. Motivo: %s%n",
                consulta.getDataHora(),
                consulta.getMedico().getNome(),
                consulta.getObservacoes());
    }
}