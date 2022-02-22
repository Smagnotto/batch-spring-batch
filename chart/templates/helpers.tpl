{{- define "helpers.list-env-variables"}}
{{- range $key, $val := .Values.appEnvironment }}
- name: {{ $key }}
  value: "{{ $val }}"
{{- end }}
{{- end }}

{{- define "helpers.list-env-variables-from-secrets"}}
{{- if .Values.application.appEnvsFromSecrets }}
{{- $appName := .Values.application.name -}}
{{- $listAppEnvsFromSecrets := split "," .Values.application.appEnvsFromSecrets -}}
{{- range $key, $val := $listAppEnvsFromSecrets }}
- name: {{ $val }}
  valueFrom:
    secretKeyRef:
      name: {{ $appName }}-{{ $val | replace "_" "-" | lower }}
      key: {{ $val }}
{{- end }}
{{- end }}
{{- end }}
